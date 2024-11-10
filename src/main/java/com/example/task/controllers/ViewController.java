package com.example.task.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/form")
    public String viewAddResume() {
        return "form";
    }

    @GetMapping("/resumes")
    public String viewResume() {
        return "listResumes";
    }
}
