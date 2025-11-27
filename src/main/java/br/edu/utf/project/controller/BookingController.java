package br.edu.utf.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.edu.utf.project.decorator.UserId;
import br.edu.utf.project.dto.CreateBookingDTO;
import br.edu.utf.project.model.BookingModel;
import br.edu.utf.project.service.BookingService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

	private final BookingService bookingService;

	@GetMapping
	public ResponseEntity<List<BookingModel>> findAll() {
		return ResponseEntity.ok(bookingService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingModel> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(bookingService.findById(id));
	}

	@PostMapping
	public ResponseEntity<BookingModel> createBooking(
			@Parameter(hidden = true) @UserId UUID userId,
			@Validated @RequestBody CreateBookingDTO dto) {

		BookingModel created = bookingService.createBooking(userId, dto);
		return ResponseEntity.status(201).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookingModel> updateBooking(
			@Parameter(hidden = true) @UserId UUID userId,
			@PathVariable UUID id,
			@Validated @RequestBody CreateBookingDTO dto) {

		BookingModel updated = bookingService.updateBooking(userId, id, dto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {

		boolean deleted = bookingService.deleteBooking(id);

		if (!deleted) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}
}
