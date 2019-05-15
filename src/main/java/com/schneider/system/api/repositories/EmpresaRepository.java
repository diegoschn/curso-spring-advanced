package com.schneider.system.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.schneider.system.api.model.Empresa;

@Repository
/**
 * 
 * @author Diego Pex
 *	A annotation transactional pode ser inserido na classe(em cima) ou nos métodos...
 *	Se no caso annotation for inserido na classe... então os métodos que compoê também serão
 *	do mesmo valor... Se no caso tiver alguma transação que precise ser aberta.. então
 *	no próprio método deverá ser colocado como readOnly = false
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
}
