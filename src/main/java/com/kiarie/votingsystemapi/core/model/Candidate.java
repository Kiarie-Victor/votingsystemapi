package com.kiarie.votingsystemapi.core.model;

import com.kiarie.votingsystemapi.accounts.model.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "core_candidate", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "election_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String faculty;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @Column(columnDefinition = "TEXT")
    private String manifesto;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
