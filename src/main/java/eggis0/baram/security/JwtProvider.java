package eggis0.baram.security;

import eggis0.baram.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;


@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String userid, Role roles) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + exp);
        return Jwts.builder()
                .setSubject(userid)
                .claim("role", roles.getRole())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        return new PreAuthenticatedAuthenticationToken(this.getEmail(token),null,  this.getRole(token));
    }
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Collection<? extends GrantedAuthority> getRole(String token) {
        return convertToGrantedAuthorities(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role").toString());
    }

    private Collection<? extends GrantedAuthority> convertToGrantedAuthorities(String role) {
        List<GrantedAuthority> authoritie = new ArrayList<>();
        authoritie.add(new SimpleGrantedAuthority(role));
        return authoritie;
    }

    public void validateToken(String token) {
        try {
            parseClaims(token);
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
//            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
//            throw new ExpiredTokenException();
        }
    }

    public Claims parseClaims(String accessToken) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(secretKey);
            return parser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}