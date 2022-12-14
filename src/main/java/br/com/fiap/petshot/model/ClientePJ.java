package br.com.fiap.petshot.model;

public class ClientePJ extends Cliente {
	private String cnpj;

	public ClientePJ(Integer id, String name, Endereco endereco, String email, String cnpj) {
		super(id, name, endereco, email, "PJ");
		this.cnpj = cnpj;
	}

	public ClientePJ() {
		super(null, null, null, null, "PJ");
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return super.toString() + " => ClientePJ{" +
				"cnpj=" + cnpj +
				'}';
	}
}
