package br.edu.utf.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utf.project.decorator.UserId;
import br.edu.utf.project.dto.CreateUserDTO;
import br.edu.utf.project.model.UserModel;
import br.edu.utf.project.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"),
	})
	public ResponseEntity<List<UserModel>> getAll() {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping("profile")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"),
	})
	public ResponseEntity<UserModel> getProfile(@Parameter(hidden = true) @UserId UUID id) {
		return ResponseEntity.ok(userService.findByIdOrFail(id));
	}

	@GetMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
			@ApiResponse(responseCode = "409", description = "Usuário já existe")
	})
	public ResponseEntity<UserModel> getById(@PathVariable UUID id) {
		return userService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
			@ApiResponse(responseCode = "409", description = "Já existe um usuário com esse e-mail"),
	})
	public ResponseEntity<UserModel> create(@RequestBody @Valid CreateUserDTO createUserDTO) {
		return ResponseEntity.ok(userService.save(createUserDTO));
	}

	@PutMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário editado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado para o id informado"),
	})
	public ResponseEntity<UserModel> update(@PathVariable UUID id, @RequestBody UserModel user) {
		return userService.update(id, user)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	})
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		boolean deleted = userService.delete(id);

		return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
