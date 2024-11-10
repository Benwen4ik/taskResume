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

    /**
     * Парсит ResumeDTO в Resume, сохраняет его в бд и вызывает функцию для получения категории
     * @param resumeDTO валидированный ResumeDTO
     * @return id добавленной записи
     */
    public int save(ResumeDTO resumeDTO) {
        Resume resume = resumeRepository.save(parseByResume(resumeDTO));
        getCategory(resume);
        log.info("Сохраненно Resume с id={}", resume.getId());
        return resume.getId();
    }

    /**
     * Выдает список Resume по категории
     * @param category категория резюме
     * @return список резюме
     */
    public List<Resume> getByCategory(Category category) {
        return resumeRepository.findByCategory(category);
    }

    /**
     * Выдает список Resume по id записи
     * @param id id записи в таблице resumes
     * @return Optional для Resume
     */
    public Optional<Resume> getById(int id) {
        return resumeRepository.findById(id);
    }

    /**
     * Возвращает все записи Resume из таблицы resumes
     * @return список из List
     */
    public List<Resume> getAll() {
        return resumeRepository.findAllByOrderByCategory();
    }

    /**
     * Изменяет поле "decide" для Resume
     * @param id id записи Resume
     * @param decide новое решение
     */
    public void changeDecide(int id, Decide decide) {
        resumeRepository.findById(id).ifPresent(resume -> resume.setDecide(decide));
        log.info("Изменение решения для резюме с id={}", id);
    }

    /**
     * Изменяет поле "category" для Resume, согласно условию
     * @param resume изменяемое Resume
     */
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
        log.info("Добавлена категория для резюме с id={}", resume.getId());
    }

    /**
     * Преобразовывает ResumeDTO в Resume
     * @param dto ResumeDTO
     * @return Resume
     */
    public Resume parseByResume(ResumeDTO dto) {
        return Resume.builder().fullName(dto.getFullName()).description(dto.getDescription())
                .level(dto.getLevel()).position(dto.getPosition()).prevCompany(dto.getPrevCompany())
                .salary(dto.getSalary()).skills(parseByListSkills(dto.getSkills())).build();
    }

    /**
     * Преобразовавает List&lt;String&gt; в List&lt;Skill&gt;
     * @param list List&lt;String&gt;
     * @return List&lt;Skills&gt;
     */
    public List<Skill> parseByListSkills(List<Integer> list) {
        return list.stream().map(a -> Skill.builder().id(a).build()).toList();
    }
}
