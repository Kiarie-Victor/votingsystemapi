package com.kiarie.votingsystemapi.accounts.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateMemberRequest {
    private UUID id;
    private String firstname;
    private String secondname;
    private String regNo;
    private String email;
    private String faculty;
    private Integer yearOfStudy;

}
