package com.schneider.system.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schneider.system.api.dtos.FuncionarioDto;
import com.schneider.system.api.model.Funcionario;
import com.schneider.system.api.response.Response;
import com.schneider.system.api.service.FuncionarioService;
import com.schneider.system.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins="*")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	public FuncionarioController() {}
	
	
	
	/**
	 * 
	 * Atualiza os dados de um funcionário.
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto funcionarioDto
			,BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Atualizando funcionário {}", funcionarioDto.toString());
		
		Response<FuncionarioDto> response = new Response<>();
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
		
		if(!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado"));
		}
		
		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando funcionário {}", result.getAllErrors());
			result.getAllErrors().forEach(error->response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));
		
		return ResponseEntity.ok().body(response);
	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		return null;
	}
	
	/**
	 * 
	 * Atualiza os dados do funcionário com base nos dados encontrados na DTO
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	
	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException{
		
		funcionario.setNome(funcionarioDto.getNome());
		
		if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
			.ifPresent(func->result.addError(new ObjectError("funcionario", "Email já existente!")));
		funcionario.setEmail(funcionarioDto.getEmail());
		}
		
		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco->funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabDia->funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
			
		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(valorHora->funcionario.setValorHora(new BigDecimal(valorHora)));
		
		if(funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
		}
		
	}
}
