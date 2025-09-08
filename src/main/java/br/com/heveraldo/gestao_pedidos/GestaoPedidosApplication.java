package br.com.heveraldo.gestao_pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestaoPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoPedidosApplication.class, args);
	}

}
