package com.castis.career.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CareerController {


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
    public String jobsPage(){
        return "/view/jobs";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "/view/faq";
    }


}
