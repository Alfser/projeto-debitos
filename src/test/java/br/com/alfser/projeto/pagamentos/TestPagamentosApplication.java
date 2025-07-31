package br.com.alfser.projeto.pagamentos;

import org.springframework.boot.SpringApplication;

public class TestPagamentosApplication {

	public static void main(String[] args) {
		SpringApplication.from(PagamentosApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
