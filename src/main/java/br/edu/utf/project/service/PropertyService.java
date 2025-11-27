package br.edu.utf.project.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.utf.project.dto.CreatePropertyDTO;
import br.edu.utf.project.model.AddressModel;
import br.edu.utf.project.model.PropertyModel;
import br.edu.utf.project.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class PropertyService {

	private final PropertyRepository propertyRepository;

	public List<PropertyModel> findAll() {
		return propertyRepository.findAll();
	}

	public Optional<PropertyModel> findById(UUID id) {
		return propertyRepository.findById(id);
	}

	public PropertyModel create(UUID ownerId, CreatePropertyDTO dto) {

		PropertyModel property = dto.toEntity();

		property.setOwnerId(ownerId);

		AddressModel address = dto.getAddress().toEntity();
		property.setAddress(address);

		return propertyRepository.save(property);
	}

	public PropertyModel update(UUID id, UUID ownerId, CreatePropertyDTO dto) {

		PropertyModel property = propertyRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imóvel não encontrado"));

		if (!property.getOwnerId().equals(ownerId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Você não tem permissão para atualizar este imóvel");
		}

		dto.applyTo(property);

		return propertyRepository.save(property);
	}

	public boolean delete(UUID id) {
		if (!propertyRepository.existsById(id))
			return false;
		propertyRepository.deleteById(id);
		return true;
	}
}
