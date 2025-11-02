package br.edu.utf.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshRequestDTO {
	@NotBlank
	private String refreshToken;
}
