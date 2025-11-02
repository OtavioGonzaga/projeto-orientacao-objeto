package br.edu.utf.project.dto;

import br.edu.utf.project.model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserModel toEntity() {
        return UserModel.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
