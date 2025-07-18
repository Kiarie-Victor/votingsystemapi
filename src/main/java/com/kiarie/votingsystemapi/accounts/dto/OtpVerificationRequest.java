package com.kiarie.votingsystemapi.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerificationRequest {
    private String regNo;
    private String otpCode;
}
