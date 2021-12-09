package com.manager.os.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.manager.os.api.domain.Tecnico;
import com.manager.os.api.dto.TecnicoDTO;
import com.manager.os.api.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

	@Autowired
	private TecnicoService service;

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/tecnicos/1
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/tecnicos | json
	 */
	@GetMapping()
	public ResponseEntity<List<TecnicoDTO>> findAll() {

		List<TecnicoDTO> listDto = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	/*
	 * curl -i -X POST -H "Content-Type: application/json" -d "{\"nome\":\"Andre Calebe Tiago Moura\",\"cpf\":\"457.677.513-82\",\"telefone\":\"(47)99881-1471\"}" http://localhost:8080/tecnicos
	 */
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnico) {
		Tecnico newObj = service.create(tecnico);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * curl -i -X PUT -H "Content-Type: application/json" -d "{\"nome\":\"Andre Calebe Tiago Moura\",\"cpf\":\"457.677.513-82\",\"telefone\":\"(47)99881-1471\"}" http://localhost:8080/tecnicos/2
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnico ) {
		TecnicoDTO obj = new TecnicoDTO(service.update(id,tecnico));
		
		return ResponseEntity.ok().body(obj);
	}
	/*
	 * curl -i -H "Content-Type: application/json" -X DELETE http://localhost:8080/tecnicos/1
	 * */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
