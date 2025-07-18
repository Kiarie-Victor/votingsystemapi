package com.kiarie.votingsystemapi.accounts.model;

import com.kiarie.votingsystemapi.accounts.model.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "TEXT")
    private String token;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
