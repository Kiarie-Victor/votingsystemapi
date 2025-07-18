package com.kiarie.votingsystemapi.accounts.controller;

import com.kiarie.votingsystemapi.accounts.dto.MemberLoginRequest;
import com.kiarie.votingsystemapi.accounts.dto.MemberRegistrationRequest;
import com.kiarie.votingsystemapi.accounts.dto.OtpVerificationRequest;
import com.kiarie.votingsystemapi.accounts.dto.RefreshTokenRequest;
import com.kiarie.votingsystemapi.accounts.model.Member;
import com.kiarie.votingsystemapi.accounts.model.Otp;
import com.kiarie.votingsystemapi.accounts.repository.MemberRepository;
import com.kiarie.votingsystemapi.accounts.repository.OtpRepository;
import com.kiarie.votingsystemapi.Utils.EmailService;
import com.kiarie.votingsystemapi.accounts.service.MemberService;
import com.kiarie.votingsystemapi.security.JTWService;
import com.kiarie.votingsystemapi.Utils.OtpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final JTWService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody MemberLoginRequest request) {
        try {
            var auth = new UsernamePasswordAuthenticationToken(request.getRegNo(), request.getPassword());
            authenticationManager.authenticate(auth);

            Member member = memberRepository.findByRegNo(request.getRegNo())
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            String otpCode = OtpUtil.generateOtp();

            Otp otp = new Otp();
            otp.setOtpCode(otpCode);
            otp.setRegNo(request.getRegNo());
            otp.setCreatedAt(LocalDateTime.now());
            otp.setVerified(false);
            otpRepository.save(otp);

            emailService.sendOtpEmail(member.getEmail(), otpCode);

            return ResponseEntity.ok(Map.of("message", "OTP sent to your registered email."));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        Otp otp = otpRepository.findTopByRegNoOrderByCreatedAtDesc(request.getRegNo())
                .filter(o -> !o.isVerified())
                .filter(o -> o.getOtpCode().equals(request.getOtpCode()))
                .filter(o -> o.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(5)))
                .orElse(null);

        if (otp == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired OTP"));
        }

        otp.setVerified(true);
        otpRepository.save(otp);

        Member member = memberRepository.findByRegNo(request.getRegNo())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        String accessToken = jwtService.generateAccessToken(Map.of(), member);
        String refreshToken = jwtService.generateRefreshToken(member);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody MemberRegistrationRequest request) {
        if (memberRepository.findByRegNo(request.getRegNo()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Registration number already exists"));
        }
        Member savedMember = memberService.create(request);

        String message = String.format("Registration successful. User ID: %s. Please login.", savedMember.getId());
        return ResponseEntity.ok(Map.of("message", message));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request) {
        String regNo;
        try {
            regNo = jwtService.extractUsername(request.getRefreshToken());
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid refresh token"));
        }

        Member member = memberRepository.findByRegNo(regNo)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!jwtService.isTokenValid(request.getRefreshToken(), member)) {
            return ResponseEntity.status(401).body(Map.of("message", "Refresh token expired or invalid"));
        }

        String newAccessToken = jwtService.generateAccessToken(Map.of(), member);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        jwtService.revokeToken(token);

        return ResponseEntity.ok(Map.of("message", "Logout successful. Token revoked."));
    }
}
