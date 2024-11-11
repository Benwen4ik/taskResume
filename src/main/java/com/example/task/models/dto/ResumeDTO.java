package com.example.task.models.dto;

import com.example.task.models.Skill;
import com.example.task.models.enums.LevelSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

/**
 * Класс DTO для Resume
 */
@Data
public class ResumeDTO {

    @NotNull
    @Size(min = 5, max = 80, message = "ФИО должно быть длиной от 5 до 80")
    private String fullName;

    @Size(min = 2, max = 20, message = "Название предыдущей компании должно быть длиной от 2 до 20")
    private String prevCompany;

    @NotNull
    @Size(min = 2, max = 20, message = "Должность должна быть длиной от 2 до 20")
    private String position;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LevelSkills level;

    @NotNull
    @Min(value = 0, message = "Зарплата не может быть меньше 0")
    private int salary;

    @Size(max = 100)
    private String description;

    private List<Integer> skills;
}
