package com.kiarie.votingsystemapi.core.repository;

import com.kiarie.votingsystemapi.core.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
