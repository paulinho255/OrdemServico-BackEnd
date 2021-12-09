package com.manager.os.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manager.os.api.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	@Query("SELECT obj FROM TB_TECNICO obj WHERE obj.cpf =:cpf")
	Tecnico findByCpf(@Param(value = "cpf") String cpf);

}
