package com.dxc.restcontroller.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.dxc.restcontroller.Constants;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter  {
	
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		
		if (token != null) {
			
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser()
						.setSigningKey(Constants.SUPER_SECRET_KEY)
						.parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
						.getBody()
						.getSubject();
			
			//Se recuperan los roles
			@SuppressWarnings("unchecked")
			List<String> scopes = Jwts.parser()
								.setSigningKey(Constants.SUPER_SECRET_KEY)
								.parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
								.getBody()
								.get("scopes", List.class);
			
			Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
			
			for(String  scope : scopes){
				auths.add(new SimpleGrantedAuthority(scope));
			}
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<GrantedAuthority>(auths));
			}
			return null;
		}
		return null;
	}
	
}
