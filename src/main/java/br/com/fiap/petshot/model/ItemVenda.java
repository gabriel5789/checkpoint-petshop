package br.com.fiap.petshot.model;

import br.com.fiap.petshot.validation.FormPedido;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ItemVenda {
	@NotNull(message = "Produto não encontrado")
	private Produto produto;
	@NotNull(message = "Quantidade é obrigatória")
	@Min(value = 1, message = "Quantidade deve ser maior que 0")
	private Integer quantidade;
	private Double preco;
	private Vendedor vendedorAprovador;

	public ItemVenda(Produto produto, Integer quantidade, Double preco, Vendedor vendedorAprovador) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.preco = preco;
		this.vendedorAprovador = vendedorAprovador;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Vendedor getVendedorAprovador() {
		return vendedorAprovador;
	}

	public void setVendedorAprovador(Vendedor vendedorAprovador) {
		this.vendedorAprovador = vendedorAprovador;
	}

	@Override
	public String toString() {
		return "ItemVenda{" +
				"produto=" + produto +
				", quantidade=" + quantidade +
				", preco=" + preco +
				", vendedorAprovador=" + vendedorAprovador +
				'}';
	}
}
