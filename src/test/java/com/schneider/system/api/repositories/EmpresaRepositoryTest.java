package com.schneider.system.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.schneider.system.api.model.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empRepo;
	
	private static final String cnpj = "07395695000105";
	
	/**
	 * 
	 * @throws Exception
	 * O before é executado antes de fazer qualquer teste
	 */
	@Before
	public void setUp() throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(cnpj);
		this.empRepo.save(empresa);
		
	}
	
	/**
	 * After é executado após a execução de teste
	 */
	@After
	public final void tearDown() {
		this.empRepo.deleteAll();
	}
	
	/**
	 * @test serve para informar que o método será um teste.
	 */
	@Test
	public void testBuscarPorCnpj() {
		Empresa empresa = this.empRepo.findByCnpj(cnpj);
		
		/**
		 * compara o que foi digitado com o que está cadastrado.
		 */
		assertEquals(cnpj, empresa.getCnpj());
	}
}
