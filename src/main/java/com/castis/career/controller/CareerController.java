package com.castis.career.controller;

import com.castis.career.entity.Apply;
import com.castis.career.entity.Board;
import com.castis.career.service.ApplyService;
import com.castis.career.service.BoardService;
import com.castis.career.service.MailInfoService;
import com.castis.career.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
public class CareerController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailInfoService mailInfoService;

    @GetMapping("")
    public String rootRecruit() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homepage(){

        return "view/home";
    }

    @GetMapping("/culture")
    public String culturePage(){
        return "view/culture";
    }

    @GetMapping("/people")
    public String peoplePage(){
        return "view/people";
    }

    @GetMapping("/jobs")
    public String jobsPage(Model model){

        model.addAttribute("jobList", boardService.boardList());

        return "view/jobs";
    }

    @GetMapping("/jobDetail")
    public String jobDetailPage( Long id, Model model) throws IOException {

        model.addAttribute("jobDetails", boardService.boardView(id));

        Board board = boardService.boardView(id);
        board.setView_count(board.getView_count() + 1);
        boardService.noFileChanged(board);

        return "view/jobs_detail";
    }

    // 공고 ; 첨부 파일 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<UrlResource> downloadAttach(@PathVariable Long id) throws MalformedURLException {

        Board board_file = boardService.boardView(id);
        String board_fileName = board_file.getFilename();
        String board_orgName = board_fileName.substring(board_fileName.lastIndexOf("."));

        UrlResource resource = new UrlResource("file:" + board_file.getFilepath());
        String encodedFileName = UriUtils.encode(board_fileName, StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }

    @GetMapping("/jobApply")
    public String jobApplyPage(Long id, Model model) {

        model.addAttribute("jobDetails", boardService.boardView(id));
        model.addAttribute("successMessageInfo", mailInfoService.mailInfo("success_msg"));

        return "view/apply";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "view/faq";
    }

    @PostMapping("/apply/success/{jobId}")
    @ResponseBody
    public String applySuccess(@PathVariable("jobId") Long jobId,
                               Apply apply,
                               MultipartFile file,
                               Model model) throws IOException, MessagingException {

        model.addAttribute("successMessageInfo", mailInfoService.mailInfo("success_msg"));

        //공고 관리자에게 메일 보내기 (첨부파일 포함)
        applyService.sendAttachedEmail(apply, file);

        //공고 DB 저장
        applyService.applyWrite(jobId, apply, file);

        //공고 지원자에게 메일 보내기 (첨부파일 없음)
        applyService.sendEmail(apply);


        String redirectUrl = "/apply/successMessage";
        return redirectUrl;
    }

    @GetMapping("/apply/successMessage")
    public String applySuccessHtml(Model model){

        model.addAttribute("successMessageInfo", mailInfoService.mailInfo("success_msg"));

        return "view/apply_success";
    }


}
