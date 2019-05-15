package com.schneider.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * 
 * @author diego
 *Active Profiles serve para destacar qual 
 *tipo de profile(application.properties) o spring
 *vai usar...
 */
@ActiveProfiles("test")
public class MeuPrimeiroProjetoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
