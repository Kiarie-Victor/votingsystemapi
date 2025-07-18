package com.kiarie.votingsystemapi.accounts.mapper;

import com.kiarie.votingsystemapi.accounts.dto.UpdateMemberRequest;
import com.kiarie.votingsystemapi.accounts.model.Member;

public class UpdateMemberMapper {

    public static void updateMemberFromDto(UpdateMemberRequest dto, Member member) {
        member.setFirstname(dto.getFirstname());
        member.setSecondname(dto.getSecondname());
        member.setEmail(dto.getEmail());
        member.setRegNo(dto.getRegNo());
        member.setFaculty(dto.getFaculty());
        member.setYearOfStudy(dto.getYearOfStudy());
    }
}
