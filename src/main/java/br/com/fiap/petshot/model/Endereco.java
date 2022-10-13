package br.com.fiap.petshot.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Endereco {
    private Integer id;
    @NotBlank(message = "Logradouro obrigatório")
    private String nomeRua;
    @NotNull(message = "Número da rua obrigatório")
    private Integer nrRua;
    private String complemento;
    @NotNull(message = "CEP obrigatório")
    private Integer cep;
    @NotBlank(message = "Bairro obrigatório")
    private String bairro;
    @NotBlank(message = "Cidade obrigatória")
    private String cidade;
    @NotBlank(message = "Estado obrigatório")
    private String estado;

    public Endereco() {
    }

    public Endereco(Integer id, String nomeRua, Integer nrRua, String complemento, Integer cep, String bairro, String cidade, String estado) {
        this.id = id;
        this.nomeRua = nomeRua;
        this.nrRua = nrRua;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public Integer getNrRua() {
        return nrRua;
    }

    public void setNrRua(Integer nrRua) {
        this.nrRua = nrRua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", nomeRua='" + nomeRua + '\'' +
                ", nrRua=" + nrRua +
                ", complemento='" + complemento + '\'' +
                ", cep=" + cep +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id) && Objects.equals(nomeRua, endereco.nomeRua) && Objects.equals(nrRua, endereco.nrRua) && Objects.equals(complemento, endereco.complemento) && Objects.equals(cep, endereco.cep) && Objects.equals(bairro, endereco.bairro) && Objects.equals(cidade, endereco.cidade) && Objects.equals(estado, endereco.estado);
    }

}
