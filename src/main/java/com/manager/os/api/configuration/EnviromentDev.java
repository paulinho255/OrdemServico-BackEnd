package com.manager.os.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.manager.os.api.services.DatabaseService;

@Configuration
@Profile("dev")
public class EnviromentDev {
	/*
	 * Classe que inicia o banco de desenvolvimento, e utiliza o postagresql para inserir os dados. 
	 * */
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddl;
	
	@Autowired
	private DatabaseService dbService; 
	
	@Bean
	public boolean initDb() {

		if (this.ddl.equals("create")) {
			this.dbService.createDataFromLocal();
		}
		return false;
	}

}
