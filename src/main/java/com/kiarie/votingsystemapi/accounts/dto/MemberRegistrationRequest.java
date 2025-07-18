package com.kiarie.votingsystemapi.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegistrationRequest {
    @NotBlank
    private String firstname;

    @NotBlank
    private String secondname;

    @NotBlank
    private String regNo;

    @Email
    private String email;

    @NotBlank
    private String faculty;

    private int yearOfStudy;

    @NotBlank
    private String password;
}
