package com.dxc.restcontroller.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dxc.restcontroller.Constants;
import com.dxc.restcontroller.model.LoginModel;
import com.dxc.restcontroller.model.SuccessfulAuthenticationModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginModel loginModel = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginModel.getUsername(), loginModel.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		Gson json = new Gson();
		SuccessfulAuthenticationModel tokenModel = new SuccessfulAuthenticationModel();
		
		Claims claims = Jwts.claims().setSubject(((User)auth.getPrincipal()).getUsername());
        claims.put("scopes", ((User)auth.getPrincipal()).getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
		
		String token = Jwts.builder()
				.setIssuedAt(new Date())
				.setIssuer(Constants.ISSUER_INFO)
				.setClaims(claims)
				.setSubject(((User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
		
		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
		
		tokenModel.setToken(token);
		
		response.getWriter().write(json.toJson(tokenModel));
	}
	
}
