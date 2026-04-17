package com.example.sistema_gerenciador_financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.sistema_gerenciador_financeiro",
        "Model.Controller",
        "Model.Service"
})
public class SistemaGerenciadorFinanceiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaGerenciadorFinanceiroApplication.class, args);
    }

}
