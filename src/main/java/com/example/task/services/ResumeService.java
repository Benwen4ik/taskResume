package com.example.task.services;

import com.example.task.models.enums.Category;
import com.example.task.models.enums.Decide;
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
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ResumeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public int save(ResumeDTO resumeDTO) {
        Resume resume = resumeRepository.save(parseByResume(resumeDTO));
        getCategory(resume);
        log.info("Сохраненно Resume с id={}", resume.getId());
        return resume.getId();
    }

    public List<Resume> getByCategory(Category category) {
        return resumeRepository.findByCategory(category);
    }

    public Optional<Resume> getById(int id) {
        return resumeRepository.findById(id);
    }

    public List<Resume> getAll() {
        return resumeRepository.findAllByOrderByCategory();
    }

    public void changeDecide(int id, Decide decide) {
        resumeRepository.findById(id).ifPresent(resume -> resume.setDecide(decide));
        log.info("Изменение решения для резюме id={}", id);
    }

    public void getCategory(Resume resume) {
        Query queryWorkers = entityManager.createNativeQuery("SELECT * FROM bad_workers b WHERE b.name = :name", Map.class);
        Query queryCompany = entityManager.createNativeQuery("SELECT * FROM bad_company b WHERE b.name = :name", Map.class);
        queryWorkers.setParameter("name", resume.getFullName());
        queryCompany.setParameter("name", resume.getPrevCompany());
        if (!queryWorkers.getResultList().isEmpty()) { // отказ в принятии
            resume.setCategory(Category.Reject);
        } else if (resume.getSalary() > 3000) { // кандидаты крайней очередности
            resume.setCategory(Category.LastPriority);
        } else if ((resume.getSalary() < 1000 || resume.getSalary() > 2000) // кандидаты второй очереди
                && (resume.getLevel() == LevelSkills.Junior || resume.getLevel() == LevelSkills.Senior)
                && queryCompany.getResultList().isEmpty()) {
            resume.setCategory(Category.SecondPriority);
        } else if ((resume.getSalary() <= 2000 && resume.getSalary() >= 1000) // первоочередные кандидаты
                && resume.getLevel() == LevelSkills.Middle
                && queryCompany.getResultList().isEmpty()
                && resume.getSkills().size() > 6) {
            resume.setCategory(Category.FirstPriority);
        }
        log.info("Категоризация резюме с id={}", resume.getId());
    }

    public Resume parseByResume(ResumeDTO dto) {
        return Resume.builder().fullName(dto.getFullName()).description(dto.getDescription())
                .level(dto.getLevel()).position(dto.getPosition()).prevCompany(dto.getPrevCompany())
                .salary(dto.getSalary()).skills(parseByListSkills(dto.getSkills())).build();
    }

    public List<Skill> parseByListSkills(List<Integer> list) {
        return list.stream().map(a -> Skill.builder().id(a).build()).toList();
    }
}
