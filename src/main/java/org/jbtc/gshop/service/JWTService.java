package org.jbtc.gshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jbtc.gshop.filter.SimpleGrantedAuthorityMixin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTService implements JWTServiceContract{

    //@Value("${security.jwt.secret}")
    private
    String MKEY="Contrad21sd54asf654afasf5645as64d5as4fffgasd54a5sd4564asfagds15f2ewef5as4g54as564dfasdasdsf";
    //@Value("${security.jwt.duraciontoken}")
    private
    long EXPIRATION_DATE=3600000L;
    public static final
    String TOKEN_PREFIX="Bearer ";
    public static final
    String AUTHORIZATION_PREFIX="Authorization";


    @Override
    public String crear(Authentication auth) throws JsonProcessingException {
        String username = ((User) auth.getPrincipal()).getUsername();


        //SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        //String mkey = "Contrad21sd54asf654afasf5645as64d5as4fffgasd54a5sd4564asfagds15f2ewef5as4g54as564dfasdasdsf";
        SecretKey secretKey = Keys.hmacShaKeyFor(MKEY.getBytes());

        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        Claims claims = Jwts.claims();
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));


        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject( username )
                //.signWith(SignatureAlgorithm.HS512,"123".getBytes())
                //.signWith(secretKey)
                //.signWith(SignatureAlgorithm.HS512,mkey.getBytes())
                .signWith(secretKey, SignatureAlgorithm.HS512)
                //.setExpiration(new Date(System.currentTimeMillis() + 3600000*4)).compact()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_DATE))
                .compact();
        return token ;
    }

    @Override
    public boolean validar(String token) {
        try {
            /*Jwts.parserBuilder()
                    .requireAudience("string")
                    .build()
                    .parse(header.replace("Bearer",""));*/
            getClaims(token);
            return true;
        }catch (JwtException e){return false;}
    }

    @Override
    public Claims getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(MKEY.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(resolverToken(token))
                .getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = getClaims(token).get("authorities");

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

        authorities.forEach(grantedAuthority -> {
            System.out.println(" autoridad: "+grantedAuthority);
        });
        return authorities;
    }

    @Override
    public String resolverToken(String token) {
        if(token!=null && token.startsWith(TOKEN_PREFIX))
            return token.replace(TOKEN_PREFIX,"");
        else return null;
    }
}
