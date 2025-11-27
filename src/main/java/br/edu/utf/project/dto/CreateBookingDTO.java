package br.edu.utf.project.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBookingDTO {

    @NotNull(message = "O ID do imóvel é obrigatório")
    private UUID propertyId;

    @NotNull(message = "A data inicial é obrigatória")
    @FutureOrPresent(message = "A data inicial deve ser hoje ou no futuro")
    private LocalDate startDate;

    @NotNull(message = "A data final é obrigatória")
    @FutureOrPresent(message = "A data final deve ser hoje ou no futuro")
    private LocalDate endDate;
}
