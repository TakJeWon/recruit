package com.castis.career.controller;

import com.castis.career.entity.Board;
import com.castis.career.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        model.addAttribute("boardList", boardService.boardList());

        return "/view/jobs";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "/view/faq";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "/adminView/adminDashboard";
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
    public String adminAddBoardSuccess(Board board){

        boardService.write(board);

        return "redirect:/boardSetting";
     }

    @GetMapping("/register/delete")
    public String adminBoardDelete(Integer id){
        boardService.boardDelete(id);

        return "redirect:/boardSetting";
     }

     @GetMapping("/boardSetting/modify/{id}")
     public String adminBoardEdit(@PathVariable("id") Integer id){
        return "/adminView/adminEditBoard";
     }

}
