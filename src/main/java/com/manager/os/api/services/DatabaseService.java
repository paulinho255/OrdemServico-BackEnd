package com.manager.os.api.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.os.api.domain.Cliente;
import com.manager.os.api.domain.OrdemServico;
import com.manager.os.api.domain.Prioridade;
import com.manager.os.api.domain.Status;
import com.manager.os.api.domain.Tecnico;
import com.manager.os.api.repositories.ClienteRepository;
import com.manager.os.api.repositories.OrdemServicoRepository;
import com.manager.os.api.repositories.TecnicoRepository;

@Service
public class DatabaseService {
	
	@Autowired
	private TecnicoRepository tecnico;
	@Autowired
	private ClienteRepository cliente;
	@Autowired
	private OrdemServicoRepository osRepository;
	
	public void createDataFromLocal() {
		
		Tecnico t1 = new Tecnico(null, "Paulo Rocha", "168.530.587-39", "(21)98876-6096");
		Tecnico t2 = new Tecnico(null, "Manuel Renato", "719.802.057-79", "(22)98842-9129");
		Tecnico t3 = new Tecnico(null, "Lara Regina", "411.353.277-00", "(21)98301-8989");

		Cliente c1 = new Cliente(null, "Manuel Anthony", "795.433.717-09", "(21)2791-9226");
		Cliente c2 = new Cliente(null, "Yasmin Marlene", "925.277.077-18", "(24)99620-9771");
		Cliente c3 = new Cliente(null, "Sarah Clarice", "537.265.467-82", "(24)98518-1058");

		OrdemServico os1 = new OrdemServico(null, Prioridade.ALTA, "A pedido do cliente", Status.ANDAMENTO, t1, c1);
		OrdemServico os2 = new OrdemServico(null, Prioridade.MEDIA, "Mudança no Caixa", Status.ABERTO, t2, c2);
		OrdemServico os3 = new OrdemServico(null, Prioridade.BAIXA, "Ajuste de luz", Status.ANDAMENTO, t3, c3);

		t1.getList().add(os1);
		t2.getList().add(os2);
		t3.getList().add(os3);

		c1.getList().add(os1);
		c2.getList().add(os2);
		c3.getList().add(os3);

		tecnico.saveAll(Arrays.asList(t1, t2, t3));
		cliente.saveAll(Arrays.asList(c1, c2, c3));
		osRepository.saveAll(Arrays.asList(os1, os2, os3));
	}
}
