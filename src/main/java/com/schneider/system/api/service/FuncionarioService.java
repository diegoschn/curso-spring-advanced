package com.schneider.system.api.service;

import java.util.Optional;

import com.schneider.system.api.model.Funcionario;

public interface FuncionarioService {
	
	/**
	 * 
	 * Persiste um funcionário na base de dados.
	 * 
	 * @param funcionario
	 * @return funcionario
	 */
	Funcionario persistir(Funcionario funcionario);
	
	
	/**
	 * Busca e retorna o funcionário dado um cpf.
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * 
	 * Busca e retorna o funcionario dado um email.
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Busca e retorna funcionario dado um id.
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorId(Long id);
}
