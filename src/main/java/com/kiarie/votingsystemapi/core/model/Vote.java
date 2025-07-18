package com.kiarie.votingsystemapi.core.model;

import com.kiarie.votingsystemapi.accounts.model.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "core_vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"voter_id", "election_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_seq")
    @SequenceGenerator(name = "vote_seq", sequenceName = "core_vote_id_seq", allocationSize = 1)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "voter_id", nullable = false)
    private Member voter;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToMany
    @JoinTable(
            name = "core_vote_candidates",
            joinColumns = @JoinColumn(name = "vote_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private Set<Candidate> candidates = new HashSet<>();

    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "tx_hash", length = 66)
    private String txHash;

    @Column(name = "block_number")
    private Long blockNumber;

    @Column(name = "from_address", length = 42)
    private String fromAddress;

    @Column(name = "to_address", length = 42)
    private String toAddress;

    @Column(name = "gas_used")
    private Long gasUsed;

    @Column(name = "tx_status")
    private Boolean txStatus;
}
