package com.kiarie.votingsystemapi.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "core_result", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"election_id", "candidate_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "total_votes")
    private Double totalVotes = 0.0;

    @Column(name = "is_winner")
    private boolean isWinner;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
