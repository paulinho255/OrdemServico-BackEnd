package com.manager.os.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.manager.os.api.dto.OrdemServicoDTO;
import com.manager.os.api.services.OrdemServicoService;

@RestController
@RequestMapping(value = "/os")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/os/2 | json
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemServicoDTO> finById(@PathVariable Integer id) {
		OrdemServicoDTO os = new OrdemServicoDTO(service.findById(id));
		return ResponseEntity.ok().body(os);
	}
	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X
	 * GET http://localhost:8080/os | json
	 */
	@GetMapping
	public ResponseEntity<List<OrdemServicoDTO>> findAll(){
		List<OrdemServicoDTO> os = service.findAll().stream().map(obj -> new OrdemServicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(os);
	}
	/*
	 * curl -i -X POST -H "Content-Type: application/json" -d "{\"prioridade\": 1,\"observacoes\": \"Reparação do piso externo.\",\"status\": 1,\"tecnico\": 3,\"cliente\": 6}" http://localhost:8080/os
	 * */
	@PostMapping
	public ResponseEntity<OrdemServicoDTO> create(@Valid @RequestBody OrdemServicoDTO obj){
		obj = new OrdemServicoDTO(service.create(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	/*
	 * curl -i -X PUT -H "Content-Type: application/json" -d "@OS.json" http://localhost:8080/os
	 * */
	@PutMapping
	public ResponseEntity<OrdemServicoDTO> update(@Valid @RequestBody OrdemServicoDTO obj){
		obj = new OrdemServicoDTO(service.update(obj));
		return ResponseEntity.ok().body(obj);
	}
}
