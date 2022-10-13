package br.com.fiap.petshot.service;

import br.com.fiap.petshot.model.Cliente;
import br.com.fiap.petshot.model.ItemVenda;
import br.com.fiap.petshot.model.Loja;
import br.com.fiap.petshot.model.Vendedor;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.ManagedBean;
import java.util.LinkedList;
import java.util.List;

@ManagedBean
@SessionScope
public class CriarPedidoService {
	private Integer id;

	private List<ItemVenda> itensVenda;

	private Cliente cliente;
	private Vendedor vendedor;
	private Loja loja;

	public CriarPedidoService() {
		itensVenda = new LinkedList<>();
	}

	public CriarPedidoService(Cliente cliente, Vendedor vendedor, Loja loja) {
		this.itensVenda = new LinkedList<>();
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.loja = loja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
}
