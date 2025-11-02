package br.edu.utf.project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.utf.project.model.UserModel;
import br.edu.utf.project.service.JwtService;
import br.edu.utf.project.service.UserService;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			try {
				String subject = jwtService.getSubject(token);

				UserModel userModel = userService.findByIdOrFail(subject);
				UserDetails userDetails = userModel.toUserDetails();

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}

}
