package com.schneider.system.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schneider.system.api.model.Funcionario;
import com.schneider.system.api.repositories.FuncionarioRepository;
import com.schneider.system.api.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionario {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}                    

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando funcionário por cpf {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando funcionario por email {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}
                
	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando funcionario por id {}", id);
		return Optional.ofNullable(this.funcionarioRepository.findOne(id));
	}

}
