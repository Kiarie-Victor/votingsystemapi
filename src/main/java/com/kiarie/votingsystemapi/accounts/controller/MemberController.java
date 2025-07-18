package com.kiarie.votingsystemapi.accounts.controller;

import com.kiarie.votingsystemapi.accounts.dto.MemberResponseDto;
import com.kiarie.votingsystemapi.accounts.dto.UpdateMemberRequest;
import com.kiarie.votingsystemapi.accounts.mapper.MemberMapper;
import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getById(@PathVariable UUID id) {
        Member member = memberService.getById(id);
        return ResponseEntity.ok(MemberMapper.toDto(member));
    }



    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable UUID id,
            @RequestBody UpdateMemberRequest request
    ) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
