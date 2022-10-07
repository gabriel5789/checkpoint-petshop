package br.com.fiap.petshot.controller;

import br.com.fiap.petshot.dao.EnderecoDAO;
import br.com.fiap.petshot.dao.ProdutoDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api")
public class UIUtilitiesController {

    @GetMapping("getCidadesByEstado/{sg}")
    public List<String> getCidadesByEstado(@PathVariable(name = "sg") String sigla) {
        try {
            return new EnderecoDAO().getCidades(sigla);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("getDescProdutoById/{id}")
    public String getDescProdutoById(@PathVariable Integer id) {
        try {
            return new ProdutoDAO().pesquisar(id).getNome();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
