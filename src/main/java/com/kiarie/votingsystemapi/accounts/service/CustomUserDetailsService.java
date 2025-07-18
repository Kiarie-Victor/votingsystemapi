package com.kiarie.votingsystemapi.accounts.service;

import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String regNo) throws UsernameNotFoundException {
        return memberRepository
                .findByRegNo(regNo)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with regNo: " + regNo));
    }
}
