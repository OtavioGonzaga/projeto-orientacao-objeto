package br.edu.utf.project.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.utf.project.dto.CreateBookingDTO;
import br.edu.utf.project.model.BookingModel;
import br.edu.utf.project.model.PropertyModel;
import br.edu.utf.project.repository.BookingRepository;
import br.edu.utf.project.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private final BookingRepository bookingRepository;
	private final PropertyRepository propertyRepository;

	public List<BookingModel> findAll() {
		return bookingRepository.findAll();
	}

	public BookingModel findById(UUID id) {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
	}

	public BookingModel createBooking(UUID userId, CreateBookingDTO dto) {

		validateDates(dto.getStartDate(), dto.getEndDate());

		PropertyModel property = propertyRepository.findById(dto.getPropertyId())
				.orElseThrow(() -> new RuntimeException("Imóvel não encontrado"));

		int days = dto.getEndDate().compareTo(dto.getStartDate());
		BigDecimal totalPrice = BigDecimal.valueOf(property.getDailyRate().floatValue() * days);
		BookingModel booking = BookingModel.builder()
				.userId(userId)
				.property(property)
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.totalPrice(totalPrice)
				.build();

		return bookingRepository.save(booking);
	}

	public BookingModel updateBooking(UUID userId, UUID bookingId, CreateBookingDTO dto) {

		validateDates(dto.getStartDate(), dto.getEndDate());

		BookingModel booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

		if (!booking.getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado à reserva");
		}

		PropertyModel property = propertyRepository.findById(dto.getPropertyId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imóvel não encontrado"));

		booking.setStartDate(dto.getStartDate());
		booking.setEndDate(dto.getEndDate());

		int days = dto.getEndDate().compareTo(dto.getStartDate());
		booking.setTotalPrice(BigDecimal.valueOf(property.getDailyRate().floatValue() * days));

		return bookingRepository.save(booking);
	}

	public boolean deleteBooking(UUID id) {
		if (!bookingRepository.existsById(id)) {
			return false;
		}
		bookingRepository.deleteById(id);
		return true;
	}

	private void validateDates(LocalDate start, LocalDate end) {
		if (end.isBefore(start)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"A data final deve ser posterior à data inicial");
		}
	}
}
