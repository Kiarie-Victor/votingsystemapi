package com.kiarie.votingsystemapi.core.repository;

import com.kiarie.votingsystemapi.core.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
