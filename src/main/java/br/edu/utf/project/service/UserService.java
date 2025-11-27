package br.edu.utf.project.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.utf.project.dto.CreateUserDTO;
import br.edu.utf.project.model.UserModel;
import br.edu.utf.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	@Qualifier("argon2")
	private final HashService hashService;

	public List<UserModel> findAll() {
		return userRepository.findAll();
	}

	public Optional<UserModel> findById(@NonNull UUID id) {
		return userRepository.findById(id);
	}

	public UserModel findByIdOrFail(@NonNull UUID id) {
		return this.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
	}

	public Optional<UserModel> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public UserModel findByEmailOrFail(String email) {
		return this.findByEmail(email)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
	}

	public UserModel save(CreateUserDTO createUserDTO) {
		UserModel user = createUserDTO.toEntity();

		boolean userAlreadyExists = this.userRepository.existsByEmail(createUserDTO.getEmail());

		if (userAlreadyExists) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com o e-mail informado");
		}

		user.setPassword(hashService.cypherString(user.getPassword()));

		return userRepository.save(user);
	}

	public Optional<UserModel> update(@NonNull UUID id, UserModel user) {
		return userRepository.findById(id).map(existing -> {
			existing.setName(user.getName());
			existing.setPassword(user.getPassword());

			return userRepository.save(existing);
		});
	}

	public boolean delete(@NonNull UUID id) {
		if (!userRepository.existsById(id))
			return false;

		userRepository.deleteById(id);

		return true;
	}
}
