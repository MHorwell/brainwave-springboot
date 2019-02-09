package com.qa.springboot.database.brainwavespringboot.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springboot.database.brainwavespringboot.exception.ResourceNotFoundException;
import com.qa.springboot.database.brainwavespringboot.model.Beach;
import com.qa.springboot.database.brainwavespringboot.repository.BeachRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BeachController {

	@Autowired
	BeachRepository repository;

	@PostMapping("/beach")
	public Beach createBeach(@Valid @RequestBody Beach sDGM) {
		return repository.save(sDGM);
	}
	
	@PostMapping("/beaches")
	public Collection<Beach> createBeaches(@Valid @RequestBody Collection<Beach> sDGM) {
		for (Beach beach : sDGM){
			repository.save(beach);
		}
		return sDGM;
	}

	@GetMapping("/beach/{id}")
	public Beach getBeachById(@PathVariable(value = "id") Long beachId) {
		return repository.findById(beachId)
				.orElseThrow(() -> new ResourceNotFoundException("SpringBootDatabase", "id", beachId));
	}

	@GetMapping("/beach/name/{name}")
	public Collection<Beach> findByName(@PathVariable(value = "name") String beachName) {
		return repository.findByNameContaining(beachName);
	}

	@GetMapping("/beach")
	public List<Beach> getAllBeachs() {
		return repository.findAll();
	}

	@PutMapping("/beach/{id}")
	public Beach updateBeach(@PathVariable(value = "id") Long beachID, @Valid @RequestBody Beach beachDetails) {
		Beach sDGM = repository.findById(beachID)
				.orElseThrow(() -> new ResourceNotFoundException("Beach", "id", beachID));
		if (beachDetails.getName() != null) {
			sDGM.setName(beachDetails.getName());
		}
		if (beachDetails.getLatitude() != 0L) {
			sDGM.setLatitude(beachDetails.getLatitude());
		}
		if (beachDetails.getLongtitude() != 0L) {
			sDGM.setLongtitude(beachDetails.getLongtitude());
		}		
		return repository.save(sDGM);
	}

	@DeleteMapping("/beach/{id}")
	public ResponseEntity<?> deleteBeach(@PathVariable(value = "id") Long beachID) {
		Beach sDGM = repository.findById(beachID)
				.orElseThrow(() -> new ResourceNotFoundException("Beach", "id", beachID));
		repository.delete(sDGM);
		return ResponseEntity.ok().build();
	}

}
