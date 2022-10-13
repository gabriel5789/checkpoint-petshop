package br.com.fiap.petshot.model;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Cliente {
    private Integer id;
    @Valid
    private Endereco endereco;
    protected String tipoCliente;
    public Cliente() {
    }
    public Cliente(Integer id, String nome, Endereco endereco, String email, String tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.tipoCliente = tipoCliente;
    }


    @NotBlank(message = "Nome obrigatório")
    private String nome;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @NotBlank(message = "Email obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                ", endereco=" + endereco +
                ", email='" + email + '\'' +
                ", tipoCliente='" + tipoCliente + '\'' +
                '}';
    }
}
