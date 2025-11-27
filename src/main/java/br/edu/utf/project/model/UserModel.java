package br.edu.utf.project.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.utf.project.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false, length = 255)
	@JsonIgnore
	private String password;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_role")
    private UserRole role = UserRole.USER;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Builder.Default()
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at", nullable = false, updatable = true)
	@Builder.Default()
	private LocalDateTime updatedAt = LocalDateTime.now();

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public UserDetails toUserDetails() {
		return User.builder()
				.username(this.email)
				.password(this.password != null ? this.password : "")
				.roles(this.role.name())
				.build();
	}
}
