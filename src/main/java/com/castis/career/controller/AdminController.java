package com.castis.career.controller;

import com.castis.career.entity.Apply;
import com.castis.career.entity.Board;
import com.castis.career.entity.MailInfo;
import com.castis.career.entity.Member;
import com.castis.career.service.ApplyService;
import com.castis.career.service.BoardService;
import com.castis.career.service.MailInfoService;
import com.castis.career.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailInfoService mailInfoService;

    @GetMapping("/")
    public String getLoginForm(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){

            //로그인성공
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/adminLogin";
    }

    @GetMapping("/adminLogin")
    public String adminLogin(){
        return "/adminView/adminLogin";
    }

    @GetMapping("/dashboard")
    public String adminPage(){
        return "adminView/adminDashboard";
    }

    @GetMapping("/boardSetting")
    public String adminBoardSetting(Model model){

        model.addAttribute("boardList", boardService.boardList());

        return "/adminView/adminBoardSetting";
    }

    @GetMapping("/register")
    public String adminAddBoard(){
        return "/adminView/adminAddBoard";
    }

    @PostMapping("/register/success")
    public String adminAddBoardSuccess(Board board, MultipartFile file) throws IOException {

        boardService.write(board, file);

        return "redirect:/admin/boardSetting";
    }

    @GetMapping("/register/delete")
    public String adminBoardDelete(Long id){
        boardService.boardDelete(id);

        return "redirect:/admin/boardSetting";
    }

    @GetMapping("/boardView")
    public String adminBoardView( Long id, Model model){

        model.addAttribute("board", boardService.boardView(id));

        return "/adminView/adminBoardView";
    }

    @PostMapping("/register/update/{id}")
    public String adminBoardUpdate(@PathVariable("id") Long id,
                                   Board board,
                                   MultipartFile file) throws IOException {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setEducation(board.getEducation());
        boardTemp.setExperience(board.getExperience());
        boardTemp.setType(board.getType());
        boardTemp.setLocation(board.getLocation());
        boardTemp.setStart_date(board.getStart_date());
        boardTemp.setEnd_date(board.getEnd_date());
        boardTemp.setFilename(board.getFilename());
        boardTemp.setFilepath(board.getFilepath());

        boardService.write(boardTemp, file);

        return "redirect:/admin/boardSetting";
    }

    // 지원서 ; 첨부 파일 다운로드
    @GetMapping("/applyDownload/{id}")
    public ResponseEntity<UrlResource> applyDownloadAttach(@PathVariable Long id) throws MalformedURLException {

        Apply apply_file = applyService.applyView(id);
        String apply_fileName = apply_file.getFilename();
        String apply_orgName = apply_fileName.substring(apply_fileName.lastIndexOf("."));

        UrlResource resource = new UrlResource("file:" + apply_file.getFilepath());
        String encodedFileName = UriUtils.encode(apply_fileName, StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }


    @GetMapping("/applySetting")
    public String adminApplySetting(Model model){

        model.addAttribute("applyList", applyService.applyList());

        return "/adminView/adminApplySetting";
    }

    @GetMapping("/applyMailSetting")
    public String adminApplyMailSetting(Model model){

        model.addAttribute("applyMailInfo", mailInfoService.mailInfo("apply"));
        model.addAttribute("adminMailInfo", mailInfoService.mailInfo("admin"));

        return "/adminView/adminApplyMailSetting";
    }

    @PostMapping("/applyMail/modify")
    public String adminApplyMailModify(MailInfo mailInfo) throws IOException {

        MailInfo mailInfoTemp = mailInfoService.mailInfo("apply");
        mailInfoTemp.setTitle(mailInfo.getTitle());
        mailInfoTemp.setContent(mailInfo.getContent());

        mailInfoService.write(mailInfoTemp);

        return "redirect:/admin/applyMailSetting";
    }

    @GetMapping("/apply/delete")
    public String adminApplyDelete(Long id){
        applyService.applyDelete(id);

        return "redirect:/admin/applySetting";
    }

    @GetMapping("/memberList")
    public String memberListView(Model model){

        model.addAttribute("memberList", memberService.MemberList());

        return "/adminView/adminMemberList";
    }

    @GetMapping("/addMember")
    public String addMemberView(){
        return "/adminView/adminAddMember";
    }

    @PostMapping("/member/success")
    public String createMember(Member member){

        memberService.save(member);
        return "redirect:/admin/memberList";
    }

    @GetMapping("/member/delete")
    public String adminMemberDelete(Long id){
        memberService.memberDelete(id);

        return "redirect:/admin/memberList";
    }

}
