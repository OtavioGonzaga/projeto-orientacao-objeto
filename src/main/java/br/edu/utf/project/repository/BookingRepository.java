package br.edu.utf.project.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.utf.project.model.BookingModel;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Long> {
	Optional<BookingModel> findById(UUID id);
	boolean existsById(UUID id);
	void deleteById(UUID id);
}
