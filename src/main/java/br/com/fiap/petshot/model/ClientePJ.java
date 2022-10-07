package br.com.fiap.petshot.model;

public class ClientePJ extends Cliente {
    private Long cnpj;

    public ClientePJ(Integer id, String name, Endereco endereco, String email, Long cnpj) {
        super(id, name, endereco, email, "PJ");
        this.cnpj = cnpj;
    }

    public ClientePJ() {
        super(null, null, null, null, "PJ");
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return super.toString() + " => ClientePJ{" +
                "cnpj=" + cnpj +
                '}';
    }
}
