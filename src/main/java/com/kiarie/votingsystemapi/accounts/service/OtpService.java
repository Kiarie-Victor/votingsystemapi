package com.kiarie.votingsystemapi.accounts.service;

import com.kiarie.votingsystemapi.accounts.model.Otp;
import com.kiarie.votingsystemapi.accounts.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public boolean isValidOtp(String regNo, String inputOtp) {
        return otpRepository.findTopByRegNoOrderByCreatedAtDesc(regNo)
                .filter(otp -> !otp.isVerified())
                .filter(otp -> otp.getOtpCode().equals(inputOtp))
                .filter(otp -> otp.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5)))
                .map(otp -> {
                    otp.setVerified(true);
                    otpRepository.save(otp);
                    return true;
                })
                .orElse(false);
    }
}
