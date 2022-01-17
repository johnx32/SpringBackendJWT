package org.jbtc.gshop.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jbtc.gshop.service.JWTService;
import org.jbtc.gshop.service.JWTServiceContract;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTServiceContract jwtServiceContract;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTServiceContract jwtServiceContract) {
        super(authenticationManager);
        this.jwtServiceContract=jwtServiceContract;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header  = request.getHeader(JWTService.AUTHORIZATION_PREFIX);
        if(header!=null && !header.startsWith(JWTService.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            System.out.println(" no empieza con Bearer");
            return;
        }else System.out.println(" Bearer incluido");

        UsernamePasswordAuthenticationToken authenticationToken = null;
        if(jwtServiceContract.validar(header)){
            authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtServiceContract.getUsername(header),
                    null,
                    jwtServiceContract.getRoles(header));
            System.out.println("token valido");
        }else System.out.println("token invalido");

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);

        //super.doFilterInternal(request, response, chain);
    }
}
