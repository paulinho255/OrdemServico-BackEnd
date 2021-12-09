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

import com.manager.os.api.domain.Cliente;
import com.manager.os.api.dto.ClienteDTO;
import com.manager.os.api.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/clientes/1
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO objDTO = new ClienteDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/clientes | json
	 */
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<ClienteDTO> listDto = service.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	/*
	 * curl -i -X POST -H "Content-Type: application/json" -d "{\"nome\":\"Andre Calebe Tiago Moura\",\"cpf\":\"457.677.513-82\",\"telefone\":\"(47)99881-1471\"}" http://localhost:8080/clientes
	 */
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO cliente) {
		Cliente newObj = service.create(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * curl -i -X PUT -H "Content-Type: application/json" -d "{\"nome\":\"Andre Calebe Tiago Moura\",\"cpf\":\"457.677.513-82\",\"telefone\":\"(47)99881-1471\"}" http://localhost:8080/clientes/2
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO cliente ) {
		ClienteDTO obj = new ClienteDTO(service.update(id,cliente));
		
		return ResponseEntity.ok().body(obj);
	}
	/*
	 * curl -i -H "Content-Type: application/json" -X DELETE http://localhost:8080/clientes/1
	 * */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
