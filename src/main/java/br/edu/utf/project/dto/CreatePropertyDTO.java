package br.edu.utf.project.dto;

import java.math.BigDecimal;

import br.edu.utf.project.model.PropertyModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePropertyDTO {

	@Valid
	@NotNull(message = "O endereço é obrigatório")
	private CreateAddressDTO address;

	@NotBlank(message = "O título é obrigatório")
	@Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres")
	private String title;

	@NotBlank(message = "A url da imagem é obrigatório")
	@Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres")
	private String imageUrl;

	@NotNull(message = "A diária é obrigatória")
	@DecimalMin(value = "0.0", inclusive = false, message = "A diária deve ser maior que zero")
	private BigDecimal dailyRate;

	@Size(max = 5000, message = "A descrição deve ter no máximo 5000 caracteres")
	private String description;

	public PropertyModel toEntity() {
		return PropertyModel.builder()
				.title(this.title)
				.dailyRate(this.dailyRate)
				.imageUrl(this.imageUrl)
				.description(this.description)
				.build();
	}

	public void applyTo(PropertyModel property) {
		if (title != null)
			property.setTitle(title);
		if (dailyRate != null)
			property.setDailyRate(dailyRate);
		if (description != null)
			property.setDescription(description);
		if (imageUrl != null)
			property.setImageUrl(imageUrl);

		if (address != null && property.getAddress() != null) {
			address.applyTo(property.getAddress());
		}
	}
}
