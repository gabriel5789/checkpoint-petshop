package br.com.fiap.petshot.controller;

import br.com.fiap.petshot.connection.Conexao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        Conexao.fechar();
        return "index";
    }
}
