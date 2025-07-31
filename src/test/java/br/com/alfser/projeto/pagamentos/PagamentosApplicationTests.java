package br.com.alfser.projeto.pagamentos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class PagamentosApplicationTests {

	@Test
	public void contextLoads() {
	}

}
