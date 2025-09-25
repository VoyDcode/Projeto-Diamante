package com.fiap.bank_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BankApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }

    @RestController
    class RootController {
        @GetMapping("/")
        public String home() {
            return "Projeto Bank - Victor Rodrigues e Renato Silva Alexandre Bezerra"; 
        }
    }
}
