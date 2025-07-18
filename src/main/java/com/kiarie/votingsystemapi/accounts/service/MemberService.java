package com.kiarie.votingsystemapi.accounts.service;

import com.kiarie.votingsystemapi.accounts.dto.MemberRegistrationRequest;
import com.kiarie.votingsystemapi.accounts.dto.MemberResponseDto;
import com.kiarie.votingsystemapi.accounts.dto.UpdateMemberRequest;
import com.kiarie.votingsystemapi.accounts.mapper.MemberMapper;
import com.kiarie.votingsystemapi.accounts.mapper.UpdateMemberMapper;
import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;


    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll().stream()
                .map(MemberMapper::toDto)
                .collect(Collectors.toList());
    }



    public Member getById(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    public Member create(MemberRegistrationRequest request) {
        Member member = memberMapper.toEntity(request);
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        return memberRepository.save(member);
    }

    public MemberResponseDto update(UUID id, UpdateMemberRequest request) {
        Member existing = getById(id);
        UpdateMemberMapper.updateMemberFromDto(request, existing);
        return MemberMapper.toDto(memberRepository.save(existing));
    }

    public void delete(UUID id) {
        memberRepository.deleteById(id);
    }
}
