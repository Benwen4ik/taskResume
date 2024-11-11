package com.example.task.models;

import com.example.task.models.enums.Category;
import com.example.task.models.enums.Decide;
import com.example.task.models.enums.LevelSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс-сущность для таблицы resumes
 */
@Entity
@Table(name = "resumes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fio")
    @NotNull
    @Size(min = 5, max = 80, message = "ФИО должно быть длиной от 5 до 80")
    private String fullName;

    @Column(name = "prev_company")
    @Size(min = 2, max = 20, message = "Название предыдущей компании должно быть длиной от 2 до 20")
    private String prevCompany;

    @Column
    @NotNull
    @Size(min = 2, max = 20, message = "Должность должна быть длиной от 2 до 20")
    private String position;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private LevelSkills level;

    @Column
    @NotNull
    @Min(value = 0, message = "Зарплата не может быть меньше 0")
    private int salary;

    @Column
    @Size(max = 100, message = "Описание должно быть максимум 100 символов")
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(EnumType.STRING)
    private Decide decide;

    @ManyToMany()
    @JoinTable(
        name = "resume_skill",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

}
