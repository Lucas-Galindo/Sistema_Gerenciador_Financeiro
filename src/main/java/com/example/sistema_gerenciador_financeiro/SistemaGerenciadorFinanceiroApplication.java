package com.example.sistema_gerenciador_financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = {"com.example.sistema_gerenciador_financeiro", "Model"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
public class SistemaGerenciadorFinanceiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGerenciadorFinanceiroApplication.class, args);
    }

}
