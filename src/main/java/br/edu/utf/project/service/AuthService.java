package br.edu.utf.project.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.utf.project.dto.AuthResponseDTO;
import br.edu.utf.project.dto.CreateUserDTO;
import br.edu.utf.project.dto.LoginDTO;
import br.edu.utf.project.model.UserModel;
import br.edu.utf.project.util.TimeInMs;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private final UserService userService;
	private final HashService hashService;
	private final JwtService jwtService;

	public AuthResponseDTO login(LoginDTO loginDTO) {
		UserModel user = this.userService.findByEmailOrFail(loginDTO.getEmail());

		boolean isPasswordCorrect = hashService.compareString(loginDTO.getPassword(), user.getPassword());

		if (!isPasswordCorrect) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
		}

		String token = jwtService.generateToken(
				user.getId().toString(),
				30 * TimeInMs.MINUTE.toMillis());

		String refreshToken = jwtService.generateToken(
				user.getId().toString(),
				7 * TimeInMs.DAY.toMillis());

		return new AuthResponseDTO(token, refreshToken);
	}

	public AuthResponseDTO refreshToken(String refreshToken) {
		String userId;
		try {
			userId = jwtService.getSubject(refreshToken);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
		}

		String token = jwtService.generateToken(userId,
				30 * TimeInMs.MINUTE.toMillis());

		String newRefreshToken = jwtService.generateToken(userId,
				7 * TimeInMs.DAY.toMillis());

		return new AuthResponseDTO(token, newRefreshToken);
	}

	public UserModel register(CreateUserDTO createUserDTO) {
		return userService.save(createUserDTO);
	}
}
