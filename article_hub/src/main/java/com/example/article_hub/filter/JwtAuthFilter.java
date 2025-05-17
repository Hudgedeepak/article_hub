package com.example.article_hub.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.article_hub.jwtService.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final List<String> allowedEndPoints = Arrays.asList("/appUser/addNewAppuser", "/appUser/login",
			"/article/getAllPublishedArticle", "/appuser/addNewAppuser", "/appuser/login","/appUser/deleteUser/{id}");

	@Autowired
	private JwtService jwtservice;

	@Autowired
	private UserDetailsService userDetailsService;

	private static final ThreadLocal<String> currentEmail = new ThreadLocal<>();


	public JwtAuthFilter(UserDetailsService userDetailsService2) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;

		if (allowedEndPoints.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
	        currentEmail.set(jwtservice.extractUsername(token));
		}
		String email = currentEmail.get();
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			if (jwtservice.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		 try {
		        filterChain.doFilter(request, response);
		    } finally {
		        currentEmail.remove();
		    }
	}

	public String getEmail() {
		 return currentEmail.get();
	}
}
