package com.kiarie.votingsystemapi.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequest {
    private String regNo;
    private String password;
}
