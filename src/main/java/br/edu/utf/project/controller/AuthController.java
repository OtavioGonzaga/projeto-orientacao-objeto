package br.edu.utf.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utf.project.dto.AuthResponseDTO;
import br.edu.utf.project.dto.CreateUserDTO;
import br.edu.utf.project.dto.LoginDTO;
import br.edu.utf.project.dto.RefreshRequestDTO;
import br.edu.utf.project.model.UserModel;
import br.edu.utf.project.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
        @ApiResponse(responseCode = "401", description = "E-mail ou senha incorretos")
    })
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = this.authService.login(loginDTO);

        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/refresh-token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody @Valid RefreshRequestDTO refreshRequestDTO) {
        AuthResponseDTO authResponseDto = this.authService.refreshToken(refreshRequestDTO.getRefreshToken());

        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/register")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<UserModel> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserModel user = this.authService.register(createUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
