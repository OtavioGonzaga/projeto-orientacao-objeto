package br.edu.utf.project.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.utf.project.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByEmail(String email);
	Optional<UserModel> findByEmail(String email);
}