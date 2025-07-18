package com.kiarie.votingsystemapi.core.service;

import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.repository.MemberRepository;
import com.kiarie.votingsystemapi.core.dto.ElectionStatusDto;
import com.kiarie.votingsystemapi.core.model.Election;
import com.kiarie.votingsystemapi.core.repository.ElectionRepository;
import com.kiarie.votingsystemapi.core.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final MemberRepository memberRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public ElectionStatusDto getElectionStatus(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Optional<Election> electionOpt = electionRepository
                .findFirstByFacultyAndYearOfStudyAndIsActive(member.getFaculty(), member.getYearOfStudy(), true);

        if (electionOpt.isEmpty()) {
            throw new RuntimeException("No active election found for this member");
        }

        Election election = electionOpt.get();

        boolean hasVoted = voteRepository
                .findByVoterIdAndElectionId(member.getId(), election.getId())
                .isPresent();

        return ElectionStatusDto.builder()
                .faculty(election.getFaculty())
                .yearOfStudy(election.getYearOfStudy())
                .title(election.getTitle())
                .startTime(election.getStartTime())
                .endTime(election.getEndTime())
                .voted(hasVoted)
                .build();
    }
}
