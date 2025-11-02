package br.edu.utf.project.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

	public Optional<UserModel> findById(UUID id) {
		return userRepository.findById(id);
	}

	public UserModel save(CreateUserDTO createUserDTO) {
		UserModel user = createUserDTO.toEntity();

		user.setPassword(hashService.cypherString(user.getPassword()));

		return userRepository.save(user);
	}

	public Optional<UserModel> update(UUID id, UserModel user) {
		return userRepository.findById(id).map(existing -> {
			existing.setName(user.getName());
			existing.setPassword(user.getPassword());

			return userRepository.save(existing);
		});
	}

	public boolean delete(UUID id) {
		if (!userRepository.existsById(id))
			return false;

		userRepository.deleteById(id);

		return true;
	}
}
