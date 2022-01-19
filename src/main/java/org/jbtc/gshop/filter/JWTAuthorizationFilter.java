package org.jbtc.gshop.filter;

import org.jbtc.gshop.service.JWTService;
import org.jbtc.gshop.service.JWTServiceContract;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@CrossOrigin(origins = "http://localhost:5500/")
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTServiceContract jwtServiceContract;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTServiceContract jwtServiceContract) {
        super(authenticationManager);
        this.jwtServiceContract=jwtServiceContract;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("request: "+request.getQueryString());
        String header  = request.getHeader(JWTService.AUTHORIZATION_PREFIX);
        if(header!=null && !header.startsWith(JWTService.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            System.out.println(" no empieza con Bearer");
            return;
        }else if(header==null) System.out.println("header es null");
        else System.out.println(" Bearer incluido");

        UsernamePasswordAuthenticationToken authenticationToken = null;
        System.out.println("headers: "+header);
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
