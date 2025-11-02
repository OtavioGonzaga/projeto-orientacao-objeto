package br.edu.utf.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "Email inválido")
	@Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
	private String email;

	@NotBlank(message = "A senha é obrigatória")
	private String password;
}
