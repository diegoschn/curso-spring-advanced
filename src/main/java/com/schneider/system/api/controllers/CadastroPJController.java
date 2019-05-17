package com.schneider.system.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schneider.system.api.dtos.CadastroPJDto;
import com.schneider.system.api.enums.PerfilEnum;
import com.schneider.system.api.model.Empresa;
import com.schneider.system.api.model.Funcionario;
import com.schneider.system.api.response.Response;
import com.schneider.system.api.service.EmpresaService;
import com.schneider.system.api.service.FuncionarioService;
import com.schneider.system.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastro-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPJController() {}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		
		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPjDto(funcionario));
		return ResponseEntity.ok(response);
		
	}

	/**
	 * 
	 * Popula o DTO de cadastro com os dados do funcionário e empresa.
	 * 
	 * @param funcionario
	 * @return cadastro
	 */
	private CadastroPJDto converterCadastroPjDto(Funcionario funcionario) {
		CadastroPJDto cadastro = new CadastroPJDto();
		cadastro.setId(funcionario.getId());
		cadastro.setNome(funcionario.getNome());
		cadastro.setEmail(funcionario.getEmail());
		cadastro.setCpf(funcionario.getCpf());
		cadastro.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastro.setCnpj(funcionario.getEmpresa().getCnpj());
		return cadastro;
	}
	
	/**
	 * 
	 * Converte os dados DTO para funcionário.
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return funcionario
	 * @throws NoSuchAlgorithmException
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		return funcionario;
	}
	/**
	 * 
	 * Converte os dados do DTO para empresa
	 * 
	 * @param cadastroPJDto
	 * @return empresa
	 */
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		
		return empresa;
	}
	
	/**
	 * Verifica se a empresa ou funcionário já existem na base de dados;
	 * 
	 * @param cadastroPJDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
		
		this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
		.ifPresent(emp->result.addError(new ObjectError("empresa", "Empresa já existente.")));
		
		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
		.ifPresent(func->result.addError(new ObjectError("funcionario", "CPF já existente")));
		
		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
		.ifPresent(func->result.addError(new ObjectError("funcionario", "Email já existente")));
	}
}
