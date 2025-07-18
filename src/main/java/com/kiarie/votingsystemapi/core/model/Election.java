package com.kiarie.votingsystemapi.core.model;

import com.kiarie.votingsystemapi.accounts.model.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "core_election", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"faculty", "year_of_study", "start_time"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String faculty;

    @Column(name = "year_of_study", nullable = false)
    private Integer yearOfStudy;

    @Column(nullable = false)
    private String title;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Member createdBy;

    @Column(name = "number_of_winners", nullable = false)
    private Integer numberOfWinners = 3;

    @Column(name = "allowed_candidates", nullable = false)
    private Integer allowedCandidates = 2;
}
