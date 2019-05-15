package com.schneider.system.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.schneider.system.api.model.Funcionario;

@Repository
/**
 * 
 * @author Diego Pex
 *	Transactional - Desabilitar/Habilitar transação SQL, como no caso abaixo é somente
 *	consulta, então não haverá abertura de transação
 */
@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	Funcionario findByCpf(String cpf);
	
	Funcionario findByEmail(String email);
	
	Funcionario findByCpfOrEmail(String cpf, String email);
}
