package br.edu.utf.project.dto;

import br.edu.utf.project.model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
    private String name;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
    private String password;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    private String email;

    public UserModel toEntity() {
        return UserModel.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
