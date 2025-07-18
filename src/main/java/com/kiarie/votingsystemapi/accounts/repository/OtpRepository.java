package com.kiarie.votingsystemapi.accounts.repository;

import com.kiarie.votingsystemapi.accounts.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<Otp, UUID> {
    Optional<Otp> findTopByRegNoOrderByCreatedAtDesc(String regNo);
}
