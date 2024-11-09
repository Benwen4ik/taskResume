package com.example.task.controllers;

import com.example.task.models.Resume;
import com.example.task.models.dto.ResumeDTO;
import com.example.task.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/add")
    private void postResume(@RequestBody ResumeDTO resume) {
        resumeService.save(resume);
    }
}
