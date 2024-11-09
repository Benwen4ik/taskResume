package com.example.task.repositories;

import com.example.task.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    Resume findByFullName(String fullName);

}
