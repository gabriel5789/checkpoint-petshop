package br.com.fiap.petshot.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Loja {
    private Integer id;
    @Valid
    private Endereco endereco;

    public Loja(Integer id, Endereco endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Loja{" +
                "id=" + id +
                ", endereco=" + endereco +
                '}';
    }
}
