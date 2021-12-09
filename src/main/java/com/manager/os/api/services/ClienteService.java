package com.manager.os.api.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.os.api.domain.Pessoa;
import com.manager.os.api.domain.Cliente;
import com.manager.os.api.dto.ClienteDTO;
import com.manager.os.api.exceptions.DataIntegrityViolationException;
import com.manager.os.api.exceptions.ObjectNotFoundException;
import com.manager.os.api.repositories.PessoaRepository;
import com.manager.os.api.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. " + id + ", tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO obj) {
		if(findByCpf(obj) != null) {
			throw new DataIntegrityViolationException("CPF ja cadastrado na base de dados.");
		}
		Cliente cliente = new Cliente(null, obj.getNome(), obj.getCpf(), obj.getTelefone());
		return clienteRepository.save(cliente);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO cliente) {
		Cliente oldObj = findById(id);
		
		if (findByCpf(cliente) != null && findByCpf(cliente).getId() != id) {
			throw new DataIntegrityViolationException("CPF ja cadastrado na base de dados.");
		}
		oldObj.setCpf(cliente.getCpf());
		oldObj.setNome(cliente.getNome());
		oldObj.setTelefone(cliente.getTelefone());
		
		return clienteRepository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		if(obj.getList().size() > 0 ) {
			throw new DataIntegrityViolationException("Este cliente possui ordens de serviço vinculada. Não pode ser deletado.");
		}
		clienteRepository.deleteById(id);
	}
	private Pessoa findByCpf(ClienteDTO obj) {
		Pessoa cliente = pessoaRepository.findByCpf(obj.getCpf());
		if (cliente != null) {
			return cliente;
		}
		return null;
	}
	

}
