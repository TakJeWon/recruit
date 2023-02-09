package com.castis.career.controller;

import com.castis.career.entity.Board;
import com.castis.career.service.BoardService;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.annotation.Resource;
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
public class CareerController {

    @Autowired
    private BoardService boardService;


    @GetMapping("/")
    public String root() {
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

    // 첨부 파일 다운로드
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
    public String jobApplyPage( Integer id, Model model){

        model.addAttribute("jobDetails", boardService.boardView(id));

        return "/view/apply";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "/view/faq";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "/adminView/adminLogin";
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

        return "redirect:/boardSetting";
     }

    @PostMapping("/apply/success")
    public String applySuccess(Board board){

        return "redirect:/jobs";
    }

    @GetMapping("/register/delete")
    public String adminBoardDelete(Integer id){
        boardService.boardDelete(id);

        return "redirect:/boardSetting";
     }

     @GetMapping("/boardView")
     public String adminBoardView( Integer id, Model model){

        model.addAttribute("board", boardService.boardView(id));

        return "/adminView/adminBoardView";
     }

     @PostMapping("/register/update/{id}")
     public String adminBoardUpdate(@PathVariable("id") Integer id,
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

        boardService.write(boardTemp, file);

        return "redirect:/boardSetting";
     }


}
