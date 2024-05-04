package project.gourmetinventoryproject.api.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

//O "GerenciadorTokenJwt" é uma classe responsável por gerar e validar tokens JWT (JSON
//Web Tokens) usados para autenticar usuários em um sistema seguro.

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String getUsernameFromToken(String token) {
        return getClaimForToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication) {
        //verificações de permissões;
        final String authoriities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder().setSubject(authentication.getName())
                .signWith(parseSecret()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000)).compact();
    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExptred(token));
    }

    private boolean isTokenExptred(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(parseSecret()).build().parseClaimsJws(token).getBody();
    }

    private SecretKey parseSecret(){
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }
}
