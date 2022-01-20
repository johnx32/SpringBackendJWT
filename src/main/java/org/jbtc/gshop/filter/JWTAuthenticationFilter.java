package org.jbtc.gshop.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbtc.gshop.entidad.Usuario;
import org.jbtc.gshop.service.JWTService;
import org.jbtc.gshop.service.JWTServiceContract;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

//@CrossOrigin(origins = "http://localhost:5500/")
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTServiceContract jwtServiceContract;


	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,JWTServiceContract jwtServiceContract) {
		//super();
		this.authenticationManager = authenticationManager;
		this.jwtServiceContract=jwtServiceContract;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if(username==null) username="";
		if(password==null) password="";


		if(username.isEmpty()&&password.isEmpty()){
		    //la data no viene por form-data sino por json
            Usuario user = null;
            try {
                user = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
                username = user.getUsername();
                password = user.getPassword();
            }catch (Exception e){}
        }

		username=username.trim();
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
			
		//System.out.println("credenciales : "+username+" "+password);
		
		return authenticationManager.authenticate(authToken);
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = jwtServiceContract.crear(authResult);

		response.addHeader(JWTService.AUTHORIZATION_PREFIX, JWTService.TOKEN_PREFIX+token);
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("token",token);
		body.put("user",(User) authResult.getPrincipal());
		body.put("mensaje","sesion iniciada");
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
		//super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException failed) throws IOException, ServletException {
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("mensaje","Error de autenticacion: username o password incorrecto!");
		body.put("error",failed.getMessage());

		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
		//super.unsuccessfulAuthentication(request, response, failed);
	}
}
