package com.manager.os.api.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.os.api.domain.Pessoa;
import com.manager.os.api.domain.Tecnico;
import com.manager.os.api.dto.TecnicoDTO;
import com.manager.os.api.exceptions.DataIntegrityViolationException;
import com.manager.os.api.exceptions.ObjectNotFoundException;
import com.manager.os.api.repositories.PessoaRepository;
import com.manager.os.api.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. " + id + ", tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO obj) {
		if(findByCpf(obj) != null) {
			throw new DataIntegrityViolationException("CPF ja cadastrado na base de dados.");
		}
		Tecnico tecnico = new Tecnico(null, obj.getNome(), obj.getCpf(), obj.getTelefone());
		return tecnicoRepository.save(tecnico);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO tecnico) {
		Tecnico oldObj = findById(id);
		
		if (findByCpf(tecnico) != null && findByCpf(tecnico).getId() != id) {
			throw new DataIntegrityViolationException("CPF ja cadastrado na base de dados.");
		}
		oldObj.setCpf(tecnico.getCpf());
		oldObj.setNome(tecnico.getNome());
		oldObj.setTelefone(tecnico.getTelefone());
		
		return tecnicoRepository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0 ) {
			throw new DataIntegrityViolationException("Este tecnico possui, ordens de serviço vinculada. Não pode ser deletado.");
		}
		tecnicoRepository.deleteById(id);
	}
	private Pessoa findByCpf(TecnicoDTO obj) {
		Pessoa tecnico = pessoaRepository.findByCpf(obj.getCpf());
		if (tecnico != null) {
			return tecnico;
		}
		return null;
	}
	

}
