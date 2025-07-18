package com.kiarie.votingsystemapi.accounts.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts_member")
public class Member extends AbstractPersistable<UUID> implements UserDetails {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String secondname;

    @Column(nullable = false, unique = true)
    private String regNo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String faculty;

    @Column(nullable = false)
    private Integer yearOfStudy;

    @Column(nullable = false)
    private String password;

    private boolean isActive = true;
    private boolean isStaff = false;
    private boolean isSuperuser = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.regNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
