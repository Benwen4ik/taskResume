package com.example.task.repositories;

import com.example.task.models.Resume;
import com.example.task.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    Resume findByFullName(String fullName);

    List<Resume> findByCategory(Category category);

    List<Resume>  findAllByOrderByCategory();

}
