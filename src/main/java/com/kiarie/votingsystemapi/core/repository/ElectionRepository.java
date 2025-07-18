package com.kiarie.votingsystemapi.core.repository;

import com.kiarie.votingsystemapi.core.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    Optional<Election> findFirstByFacultyAndYearOfStudyAndIsActive
            (String faculty, Integer yearOfStudy, boolean isActive);

}
