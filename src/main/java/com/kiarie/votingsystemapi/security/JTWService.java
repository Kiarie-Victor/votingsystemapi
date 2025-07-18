package com.kiarie.votingsystemapi.security;

import com.kiarie.votingsystemapi.accounts.model.Token;
import com.kiarie.votingsystemapi.accounts.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.kiarie.votingsystemapi.accounts.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class  JTWService {

    private static final String SECRET_KEY = "c29tZS1zZWN1cmUtc2VjcmV0LWtleS1zaG91bGQtYmUtYmFzZTY0LWVuY29kZWQ=";
    private static final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 60;
    private static final long REFRESH_TOKEN_EXPIRY = 1000 * 60 * 60 * 24 * 7;

    private final TokenRepository tokenRepository;

    private Key getSigningKey() {
        byte[] byteCode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteCode);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, Member member) {
        return saveToken(buildToken(extraClaims, member, ACCESS_TOKEN_EXPIRY), member);
    }

    public String generateRefreshToken(Member member) {
        return saveToken(buildToken(Map.of(), member, REFRESH_TOKEN_EXPIRY), member);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expirationMillis) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaimsJWT(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaimsJWT(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean revoked = tokenRepository.findByToken(token)
                .map(Token::isRevoked)
                .orElse(true);

        return username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token) &&
                !revoked;
    }

    private boolean isTokenExpired(String token) {
        return extractClaimsJWT(token).getExpiration().before(new Date());
    }

    public void revokeToken(String token) {
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            tokenRepository.save(t);
        });
    }

    private String saveToken(String token, Member member) {
        Token tokenEntity = Token.builder()
                .token(token)
                .revoked(false)
                .member(member)
                .build();
        tokenRepository.save(tokenEntity);
        return token;
    }
}
