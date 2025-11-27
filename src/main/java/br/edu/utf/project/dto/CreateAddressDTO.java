package br.edu.utf.project.dto;

import br.edu.utf.project.model.AddressModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAddressDTO {

	@NotBlank(message = "A rua é obrigatória")
	@Size(max = 255, message = "A rua deve ter no máximo 255 caracteres")
	private String street;

	@NotBlank(message = "O número é obrigatório")
	@Size(max = 50, message = "O número deve ter no máximo 50 caracteres")
	private String number;

	@NotBlank(message = "A cidade é obrigatória")
	@Size(max = 255, message = "A cidade deve ter no máximo 255 caracteres")
	private String city;
	
	@NotBlank(message = "O estado é obrigatório")
	@Size(max = 2, message = "O estado deve ter exatamente 2 caracteres (sigla)")
	private String state;
	
	@NotBlank(message = "O país é obrigatório")
	@Size(max = 255, message = "A cidade deve ter no máximo 255 caracteres")
	private String country;

	@NotBlank(message = "O CEP é obrigatório")
	@Size(min = 8, max = 8, message = "CEP deve estar no formato 00000000")
	private String zipCode;

	public AddressModel toEntity() {
		return AddressModel.builder()
				.street(this.street)
				.number(this.number)
				.city(this.city)
				.state(this.state)
				.country(this.country)
				.zipCode(this.zipCode)
				.build();
	}

	public void applyTo(AddressModel address) {
		if (street != null)
			address.setStreet(street);
		if (number != null)
			address.setNumber(number);
		if (city != null)
			address.setCity(city);
		if (state != null)
			address.setState(state);
		if (country != null)
			address.setCountry(country);
		if (zipCode != null)
			address.setZipCode(zipCode);
	}
}
