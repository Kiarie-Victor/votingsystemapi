package com.kiarie.votingsystemapi.core.repository;

import com.kiarie.votingsystemapi.core.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Object> findByVoterIdAndElectionId(UUID voter_id, Long election_id);
}
