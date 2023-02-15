package com.castis.career.controller;

import com.castis.career.dto.MailAttachedDTO;
import com.castis.career.dto.MailDTO;
import com.castis.career.entity.Apply;
import com.castis.career.entity.Board;
import com.castis.career.entity.Member;
import com.castis.career.service.ApplyService;
import com.castis.career.service.BoardService;
import com.castis.career.service.MemberService;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.thymeleaf.spring5.processor.SpringInputRadioFieldTagProcessor;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class CareerController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MemberService memberService;
//
    @GetMapping("/")
    public String rootRecruit() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homepage(){

        return "/view/home";
    }

    @GetMapping("/culture")
    public String culturePage(){
        return "/view/culture";
    }

    @GetMapping("/people")
    public String peoplePage(){
        return "/view/people";
    }

    @GetMapping("/jobs")
    public String jobsPage(Model model){

        model.addAttribute("jobList", boardService.boardList());

        return "/view/jobs";
    }

    @GetMapping("/jobDetail")
    public String jobDetailPage( Integer id, Model model){

        model.addAttribute("jobDetails", boardService.boardView(id));

        return "/view/jobs_detail";
    }

    // 공고 ; 첨부 파일 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<UrlResource> downloadAttach(@PathVariable Integer id) throws MalformedURLException {

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
    public String jobApplyPage(Integer id, Model model){

        model.addAttribute("jobDetails", boardService.boardView(id));

        return "/view/apply";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "/view/faq";
    }

    @PostMapping("/apply/success/{jobId}")
    public String applySuccess(@PathVariable("jobId") Integer jobId,
                               Apply apply,
                               MultipartFile file) throws IOException, MessagingException {

        //공고 관리자에게 메일 보내기 (첨부파일 포함)
//        MailAttachedDTO mail = new MailAttachedDTO();
//
//        mail.setAddress(apply.getEmail());
//        mail.setFileName(apply.getFilename());
//        mail.setContent("캐스트이즈 채용공고에 지원해주셔서 감사합니다.");
//        mail.setTitle("[캐스트이즈]지원해주셔서 감사합니다.");
//        mail.setCcAddress("taknineball@castis.com");
//
//        applyService.sendAttachedEmail(mail, file);

        //공고 지원자에게 메일 보내기 (첨부파일 없음)
        MailDTO applyMail = new MailDTO();

        applyMail.setAddress(apply.getEmail());
        applyMail.setContent("캐스트이즈 채용공고에 지원해주셔서 감사합니다.");
        applyMail.setTitle("[캐스트이즈]지원해주셔서 감사합니다.");

        applyService.sendEmail(applyMail);

        //공고 DB 저장
        applyService.applyWrite(jobId, apply, file);

        return "redirect:/jobs";
    }

    @GetMapping("/applySetting")
    public String adminApplySetting(Model model){

        model.addAttribute("applyList", applyService.applyList());

        return "/adminView/adminApplySetting";
    }

}
