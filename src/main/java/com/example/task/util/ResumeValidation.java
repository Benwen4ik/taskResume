package com.example.task.util;

import com.example.task.models.Resume;
import com.example.task.repositories.ResumeRepository;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResumeValidation implements Validator {

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeValidation(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Resume.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Resume resume = (Resume) target;
        if (resumeRepository.findByFullName(resume.getFullName()) != null) {
            errors.rejectValue("fullName", "Резюме с таким ФИО уже существует");
        }
    }
}
