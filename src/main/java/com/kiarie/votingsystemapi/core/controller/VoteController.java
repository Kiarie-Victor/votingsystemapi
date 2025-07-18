package com.kiarie.votingsystemapi.core.controller;

import com.kiarie.votingsystemapi.core.dto.ElectionStatusDto;
import com.kiarie.votingsystemapi.core.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/status/{memberId}")
    public ResponseEntity<ElectionStatusDto> getVotingStatus(@PathVariable UUID memberId) {
        ElectionStatusDto status = voteService.getElectionStatus(memberId);
        return ResponseEntity.ok(status);
    }
}
