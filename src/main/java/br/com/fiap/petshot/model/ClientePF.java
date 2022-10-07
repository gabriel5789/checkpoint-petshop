package br.com.fiap.petshot.model;

public class ClientePF extends Cliente {
    private Long cpf;

    public ClientePF(Integer id, String name, Endereco endereco, String email, Long cpf) {
        super(id, name, endereco, email, "PF");
        this.cpf = cpf;
    }

    public ClientePF() {
        super(null, null, null, null, "PF");
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return super.toString() + " => ClientePF{" +
                "cpf=" + cpf +
                '}';
    }
}
