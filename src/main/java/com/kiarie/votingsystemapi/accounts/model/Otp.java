package com.kiarie.votingsystemapi.accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts_otp")
public class Otp extends AbstractPersistable<UUID> {

    @Column(length = 6, nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private String regNo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean isVerified = false;
}
