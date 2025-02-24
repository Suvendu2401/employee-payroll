package com.epam.campus.repository;

import com.epam.campus.model.Department;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    boolean existsByName(String departmentName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Department d WHERE d.name = :departmentName")
    void deleteByName(String departmentName);
}
