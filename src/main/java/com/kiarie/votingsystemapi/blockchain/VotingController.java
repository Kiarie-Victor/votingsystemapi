package com.kiarie.votingsystemapi.blockchain;

import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.repository.MemberRepository;
import com.kiarie.votingsystemapi.core.model.Candidate;
import com.kiarie.votingsystemapi.core.model.Election;
import com.kiarie.votingsystemapi.core.model.Vote;
import com.kiarie.votingsystemapi.core.repository.CandidateRepository;
import com.kiarie.votingsystemapi.core.repository.ElectionRepository;
import com.kiarie.votingsystemapi.core.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;
    private final MemberRepository memberRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    @PostMapping("/{electionId}")
    @Transactional
    public ResponseEntity<?> vote(
            @PathVariable Long electionId,
            @RequestBody VoteRequestdto request,
            @AuthenticationPrincipal Object principal
    ) {
        try {
            if (!(principal instanceof Member)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
            }

            Member user = (Member) principal;
            UUID voterId = user.getId();
            System.out.println("Logged in user ID: " + voterId);


            voteRepository.findByVoterIdAndElectionId(voterId, electionId).ifPresent(v -> {
                throw new RuntimeException("You have already voted in this election.");
            });

            Member voter = memberRepository.findById(voterId)
                    .orElseThrow(() -> new RuntimeException("Voter not found"));

            Election election = electionRepository.findById(electionId)
                    .orElseThrow(() -> new RuntimeException("Election not found"));

            List<Long> candidateIds = request.getCandidateIds();
            List<Candidate> selectedCandidates = candidateRepository.findAllById(candidateIds);

            if (selectedCandidates.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No valid candidates selected"));
            }


            TransactionReceipt tx = votingService.sendVote(electionId, candidateIds);


            Vote vote = Vote.builder()
                    .voter(voter)
                    .election(election)
                    .candidates(new HashSet<>(selectedCandidates))
                    .timestamp(LocalDateTime.now())
                    .txHash(tx.getTransactionHash())
                    .blockNumber(tx.getBlockNumber().longValue())
                    .fromAddress(tx.getFrom())
                    .toAddress(tx.getTo())
                    .gasUsed(tx.getGasUsed().longValue())
                    .txStatus(tx.isStatusOK())
                    .build();

            voteRepository.save(vote);

            return ResponseEntity.ok(Map.of(
                    "message", "Vote cast successfully",
                    "txHash", tx.getTransactionHash(),
                    "blockNumber", tx.getBlockNumber().longValue(),
                    "gasUsed", tx.getGasUsed().longValue()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Voting failed", "details", e.getMessage()));
        }
    }
}
