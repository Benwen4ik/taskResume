package com.example.task.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для отображения фронт-части
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * Отображает страницу с формой для создания resume
     */
    @GetMapping("/form")
    public String viewAddResume() {
        return "form";
    }

    /**
     * Отображает страницу с имеющимися resume
     */
    @GetMapping("/resumes")
    public String viewResume() {
        return "listResumes";
    }
}
