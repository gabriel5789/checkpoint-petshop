package br.com.fiap.petshot.model;

public class Loja {
    private Integer id;
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
