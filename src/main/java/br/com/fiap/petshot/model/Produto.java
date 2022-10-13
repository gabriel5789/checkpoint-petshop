package br.com.fiap.petshot.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Produto {
    /*
    * TODO: Adicionar grupos de validação. Um para o processo de montagem do pedido,
    *  e outro para o processo de criação do produto. Pesquisar no Google "spring validation groups"
    * */
    @NotNull(message = "Código do produto obrigatório")
    private Integer id;
    private String nome;
    private Double valor;
    @Valid
    private UnidadeMedida unidadeMedida;

    public Produto() {
    }

    public Produto(Integer id, String nome, Double valor, UnidadeMedida unidadeMedida) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", unidadeMedida=" + unidadeMedida +
                '}';
    }
}
