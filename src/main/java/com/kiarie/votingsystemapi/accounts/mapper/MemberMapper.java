package com.kiarie.votingsystemapi.accounts.mapper;

import com.kiarie.votingsystemapi.accounts.dto.MemberRegistrationRequest;
import com.kiarie.votingsystemapi.accounts.dto.MemberResponseDto;
import com.kiarie.votingsystemapi.accounts.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public static MemberResponseDto toDto(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setFirstname(member.getFirstname());
        dto.setSecondname(member.getSecondname());
        dto.setRegNo(member.getRegNo());
        dto.setEmail(member.getEmail());
        dto.setFaculty(member.getFaculty());
        dto.setYearOfStudy(member.getYearOfStudy());
        dto.setSuperuser(member.isSuperuser());
        return dto;
    }

    public Member toEntity (MemberRegistrationRequest request) {
        Member member = new Member();

        member.setFirstname(request.getFirstname());
        member.setSecondname(request.getSecondname());
        member.setRegNo(request.getRegNo());
        member.setEmail(request.getEmail());
        member.setFaculty(request.getFaculty());
        member.setYearOfStudy(request.getYearOfStudy());
        member.setPassword(request.getPassword());

        return member;
    }

}
