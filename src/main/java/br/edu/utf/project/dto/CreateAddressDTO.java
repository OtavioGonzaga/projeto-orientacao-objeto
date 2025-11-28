package br.edu.utf.project.dto;

import br.edu.utf.project.model.AddressModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAddressDTO {

	@NotBlank(message = "Street is required")
	@Size(max = 255, message = "The street must be at most 255 characters")
	private String street;

	@NotBlank(message = "Number is required")
	@Size(max = 50, message = "The number must be at most 50 characters")
	private String number;

	@NotBlank(message = "City is required")
	@Size(max = 255, message = "The city must be at most 255 characters")
	private String city;
	
	@NotBlank(message = "State is required")
	@Size(max = 2, message = "The state must be at most 2 characters")
	private String state;
	
	@NotBlank(message = "Country is required")
	@Size(max = 255, message = "The country must be at most 255 characters")
	private String country;

	@NotBlank(message = "CEP is required")
	@Size(min = 8, max = 8, message = "The zip code must be 8 characters")
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
