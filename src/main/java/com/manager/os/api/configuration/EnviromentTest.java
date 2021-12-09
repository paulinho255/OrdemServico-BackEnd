package com.manager.os.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.manager.os.api.services.DatabaseService;

@Configuration
@Profile("test")
public class EnviromentTest {
	/*
	 * Classe que instancia o banco local H2, e insere os dados iniciais na base. 
	 * */
	
	@Autowired
	private DatabaseService dbService;

	@Bean
	public void initDb() {
		this.dbService.createDataFromLocal();
	}
}
