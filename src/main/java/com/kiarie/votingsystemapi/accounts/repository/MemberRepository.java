package com.kiarie.votingsystemapi.accounts.repository;

import com.kiarie.votingsystemapi.accounts.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByRegNo(String regNo);
}
