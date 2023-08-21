package com.chordncode.pmaker.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chordncode.pmaker.application.member.CustomMemberDetailsService;
import com.chordncode.pmaker.data.entity.AuthEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
@PropertySource(
    value="file:${user.home}/Desktop/dev/web/jwt/pmaker.properties"
)
public class JwtProvider {
    
    private Key key;
    private String secret;
    private final int expireTime;
    private final CustomMemberDetailsService memberDetailsService;

    public JwtProvider(@Value("${jwt.secret}") String secret, CustomMemberDetailsService memberDetailsService){
        this.secret = secret;
        this.memberDetailsService = memberDetailsService;
        this.expireTime = 1000 * 60 * 60;
        this.key = Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String memId, List<AuthEntity> authEntityList){
        Claims claims = Jwts.claims().setSubject(memId);
        claims.put("authEntityList", authEntityList);
        Date now = new Date();
        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + expireTime))
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = memberDetailsService.loadUserByUsername(this.getMemId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getMemId(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token){
        try{
            if(!token.substring(0, "BEARER".length()).equalsIgnoreCase("BEARER ")){
                return false;
            } else {
                token = token.split(" ")[1];
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e){
            return false;
        }
    }

}
