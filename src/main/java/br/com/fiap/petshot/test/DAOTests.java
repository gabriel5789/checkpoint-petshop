package br.com.fiap.petshot.test;

import br.com.fiap.petshot.dao.*;
import br.com.fiap.petshot.model.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DAOTests {

	public static void main(String[] args) {
		// testeCliente();
		// testeProduto();
		testePedidos();
		testePedidosQuery();
	}

	public static void testePedidosQuery() {
		PedidoDAO dao = new PedidoDAO();

		try {
			List<Pedido> pedidos = dao.pesquisarPedidosPorCpf("51476896801");
			for (Pedido p :
					pedidos) {
				System.out.println(p);
				System.out.println(dao.pesquisarItensDoPedido(p));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void testePedidos() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		VendedorDAO vendedorDAO = new VendedorDAO();
		List<ItemVenda> itens = new LinkedList<>();
		ClienteDAO clienteDAO = new ClienteDAO();
		PedidoDAO dao = new PedidoDAO();

		try {
			ClientePF cl = clienteDAO.getClienteByCpf("51476896801");
			Vendedor v1 = vendedorDAO.pesquisar(1);
			Produto p1 = produtoDAO.pesquisar(1);
			Produto p2 = produtoDAO.pesquisar(6);
			Loja l1 = new LojaDAO().pesquisar(1);
			ItemVenda i1 = new ItemVenda(p1, 2, p1.getValor(), v1);
			ItemVenda i2 = new ItemVenda(p2, 1, p2.getValor(), v1);
			itens.add(i1);
			itens.add(i2);
			Pedido pedido = new Pedido(null, null, cl, v1, l1, itens);
			dao.cadastrar(pedido);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void testeProduto() {
		Produto produto = new Produto(null, "Racao peixe 300g", 5D, new UnidadeMedida(1, "UNIDADE"));
		ProdutoDAO dao = new ProdutoDAO();
		try {
			System.out.println("Cadastro");
			System.out.println(dao.cadastrar(produto));

			System.out.println("\nPesquisa");
			System.out.println(dao.pesquisar(produto.getId()));
			System.out.println(dao.pesquisarPorNome(produto.getNome()));

			System.out.println("\nLista");
			dao.listar().forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void testeCliente() {
		ClienteDAO clienteDAO = new ClienteDAO();
		Endereco endereco = new Endereco();
		endereco.setNomeRua("Rua Eusébio da Costa Braga");
		endereco.setNrRua(35);
		endereco.setCep(5632060);
		endereco.setBairro("Jd Monte Kemel");
		endereco.setComplemento("Casa 1");
		endereco.setEstado("SP");
		endereco.setCidade("São Paulo");
		ClientePF clientePF = new ClientePF(null, "Pietro", endereco, "pietro@gmail.com", "4524868951");
		ClientePJ clientePJ = new ClientePJ(null, "Gabriel LTDA", endereco, "gdg@gabltda.com", "93778130000148");

		System.out.println("\nClientes PF");
		try {
			clienteDAO.cadastrarCliente(clientePF);
			clienteDAO.getClientesPF().forEach(System.out::println);
		} catch (SQLException e) {
			//System.out.println(e.getSQLState());

		}

		System.out.println("\nCliente PJ");
		try {
			clienteDAO.cadastrarCliente(clientePJ);
			clienteDAO.getClientesPJ().forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("\nAtualizar");
		try {
			clientePF.setCpf("51476896801");
			clienteDAO.atualizar(clientePF);
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			if (e.getMessage().substring(0, 9).equals("ORA-00001"))
				System.out.println("Erro! Campo já existe");
			e.printStackTrace();
			System.out.println(e.getMessage().substring(0, 9));
		} catch (SQLException e) {
			System.out.println("sqlexception");
		}
	}
}
