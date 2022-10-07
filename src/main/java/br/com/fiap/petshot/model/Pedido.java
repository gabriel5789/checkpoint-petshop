package br.com.fiap.petshot.model;

import java.sql.Date;
import java.util.List;

public class Pedido {
    private Integer id;
    private Date dataPedido;
    private Cliente cliente;
    private Vendedor vendedor;
    private Loja loja;
    private List<ItemVenda> itensVenda;

    public Pedido(Integer id, Date dataPedido, Cliente cliente, Vendedor vendedor, Loja loja, List<ItemVenda> itensVenda) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.loja = loja;
        this.itensVenda = itensVenda;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
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

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataPedido=" + dataPedido +
                ", cliente=" + cliente +
                ", vendedor=" + vendedor +
                ", loja=" + loja +
                ", itensVenda=" + itensVenda +
                '}';
    }
}
