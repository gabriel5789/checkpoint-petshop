package br.com.fiap.petshot.model;

import org.hibernate.validator.constraints.br.CPF;

public class ClientePF extends Cliente {
	@CPF
	private String cpf;

	public ClientePF(Integer id, String name, Endereco endereco, String email, String cpf) {
		super(id, name, endereco, email, "PF");
		this.cpf = cpf;
	}

	public ClientePF() {
		super(null, null, null, null, "PF");
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return super.toString() + " => ClientePF{" +
				"cpf=" + cpf +
				'}';
	}
}
