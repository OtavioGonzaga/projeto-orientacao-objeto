package br.edu.utf.project.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookingDTO {

    @NotNull(message = "ID is required")
    private UUID propertyId;

    @NotNull(message = "Initial date is required")
    @FutureOrPresent(message = "The initial date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "The end date must be today or in the future")
    private LocalDate endDate;
}
