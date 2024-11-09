package com.example.task.models.dto;

import com.example.task.models.enums.LevelSkills;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class ResumeDTO {

    @Column(name = "fio")
    private String fullName;

    @Column(name = "prev_company")
    private String prevCompany;

    @Column
    private String position;

    @Column
    @Enumerated(EnumType.STRING)
    private LevelSkills level;

    @Column
    private int salary;

    @Column
    private String description;

    private List<Integer> skills;
}
