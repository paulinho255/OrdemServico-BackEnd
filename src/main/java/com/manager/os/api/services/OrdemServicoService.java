package com.manager.os.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.os.api.domain.Cliente;
import com.manager.os.api.domain.OrdemServico;
import com.manager.os.api.domain.Prioridade;
import com.manager.os.api.domain.Status;
import com.manager.os.api.domain.Tecnico;
import com.manager.os.api.dto.OrdemServicoDTO;
import com.manager.os.api.exceptions.ObjectNotFoundException;
import com.manager.os.api.repositories.OrdemServicoRepository;

@Service
public class OrdemServicoService {
	@Autowired
	private OrdemServicoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para o id: " + id + ", Tipo: " + OrdemServico.class.getName()));
	}
	
	public List<OrdemServico> findAll() {
		return repository.findAll();
	}

	public OrdemServico create(OrdemServicoDTO obj) {
		return saveOS(obj);
	}
	
	private OrdemServico saveOS(OrdemServicoDTO obj) {
		OrdemServico os = new OrdemServico();
		
		os.setId(obj.getId());
		os.setObservacoes(obj.getObservacoes());
		os.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		os.setStatus(Status.toEnum(obj.getStatus()));
		
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		os.setTecnico(tecnico);
		os.setCliente(cliente);
		return repository.save(os);
	}
}
