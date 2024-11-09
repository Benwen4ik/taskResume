package com.example.task.services;

import com.example.task.models.enums.Category;
import com.example.task.models.enums.LevelSkills;
import com.example.task.models.Resume;
import com.example.task.models.Skill;
import com.example.task.models.dto.ResumeDTO;
import com.example.task.repositories.ResumeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class ResumeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public void save(ResumeDTO resumeDTO) {
        if (resumeRepository.findByFullName(resumeDTO.getFullName()) != null) {
            return;
        }
        Resume resume = resumeRepository.save(parseByResume(resumeDTO));
        getCategory(resume);
    }

    public void getCategory(Resume resume) {
        Query queryWorkers = entityManager.createNativeQuery("SELECT * FROM bad_workers b WHERE b.name = :name", Map.class);
        Query queryCompany = entityManager.createNativeQuery("SELECT * FROM bad_company b WHERE b.name = :name", Map.class);
        queryWorkers.setParameter("name",resume.getFullName());
        queryCompany.setParameter("name",resume.getPrevCompany());
        if (!queryWorkers.getResultList().isEmpty()) {
            resume.setCategory(Category.Reject);
            return;
        }
        if (resume.getSalary() > 3000) {
            resume.setCategory(Category.LastPriority);
            return;
        }
        if ( (resume.getSalary()< 1000 || resume.getSalary() > 2000)
                && (resume.getLevel() == LevelSkills.Junior || resume.getLevel() == LevelSkills.Senior )
                && queryCompany.getResultList().isEmpty()) {
            resume.setCategory(Category.SecondPriority);
            return;
        }
        if ((resume.getSalary()<= 2000 && resume.getSalary() >= 1000)
                && resume.getLevel() == LevelSkills.Middle
                && queryCompany.getResultList().isEmpty()){
            resume.setCategory(Category.FirstPriority);
            return;
        }
    }

    public Resume parseByResume(ResumeDTO dto) {
        return Resume.builder().fullName(dto.getFullName()).description(dto.getDescription())
                .level(dto.getLevel()).position(dto.getPosition()).prevCompany(dto.getPrevCompany())
                .salary(dto.getSalary()).skills(parseByListSkills(dto.getSkills())).build();
    }

    public List<Skill> parseByListSkills(List<Integer> list){
        return list.stream().map(a -> Skill.builder().id(a).build()).toList();
    }
}
