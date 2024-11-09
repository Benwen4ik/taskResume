package com.example.task.models;

import com.example.task.models.enums.Category;
import com.example.task.models.enums.Decide;
import com.example.task.models.enums.LevelSkills;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
