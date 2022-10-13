package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PedidoDAO {
	private PreparedStatement ps;
	private ResultSet rs;
	private final Connection conn;
	private String sql = "";

	public PedidoDAO() {
		conn = Conexao.conectar();
	}

	public List<Pedido> pesquisarPedidosPorCpf(String cpf) throws SQLException {
		ClienteDAO clienteDAO = new ClienteDAO();
		return pesquisarPedidosPorCliente(clienteDAO.getClienteByCpf(cpf));
	}

	public List<Pedido> pesquisarPedidosPorCnpj(String cnpj) throws SQLException {
		ClienteDAO clienteDAO = new ClienteDAO();
		return pesquisarPedidosPorCliente(clienteDAO.getClienteByCnpj(cnpj));
	}

	private List<Pedido> pesquisarPedidosPorCliente(Cliente cliente) throws SQLException {
		List<Pedido> pedidos = new LinkedList<>();
		sql = "SELECT id_pedido, dt_pedido, id_cliente, tb_pedido.id_vendedor, " +
				"    tb_pedido.id_loja, tb_loja.id_endereco, nm_vendedor, tb_cidade.nm_cidade, " +
				"    tb_endereco.sg_estado, nm_rua" +
				"    FROM tb_pedido" +
				"    INNER JOIN tb_loja" +
				"    ON tb_pedido.id_loja = tb_loja.id_loja" +
				"    INNER JOIN tb_vendedor" +
				"    ON tb_pedido.id_vendedor = tb_vendedor.id_vendedor" +
				"    INNER JOIN tb_endereco" +
				"    ON tb_loja.id_endereco = tb_endereco.id_endereco" +
				"    INNER JOIN tb_cidade" +
				"    ON tb_endereco.id_cidade = tb_cidade.id_cidade" +
				"    WHERE tb_pedido.id_cliente = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, cliente.getId());
		rs = ps.executeQuery();
		while (rs.next()) {
			Endereco e = new Endereco();
			e.setCidade(rs.getString("nm_cidade"));
			e.setEstado(rs.getString("sg_estado"));
			e.setNomeRua(rs.getString("nm_rua"));
			Pedido p = new Pedido(rs.getInt("id_pedido"), rs.getDate("dt_pedido"), cliente,
					new Vendedor(rs.getInt("id_vendedor"), rs.getString("nm_vendedor")),
					new Loja(rs.getInt("id_loja"), e), null);
			pedidos.add(p);
		}
		return pedidos;
	}

	public Pedido pesquisarPedido(Integer id) throws SQLException {
		sql = "SELECT * FROM tb_pedido " +
				"    INNER JOIN tb_cliente" +
				"    ON tb_cliente.id_cliente = tb_pedido.id_pedido" +
				"    INNER JOIN tb_vendedor" +
				"    ON tb_vendedor.id_vendedor = tb_pedido.id_vendedor" +
				"    INNER JOIN tb_loja" +
				"    ON tb_loja.id_loja = tb_pedido.id_loja" +
				"    INNER JOIN tb_endereco" +
				"    ON tb_loja.id_endereco = tb_endereco.id_endereco" +
				"    INNER JOIN tb_cidade" +
				"    ON tb_cidade.id_cidade = tb_endereco.id_cidade" +
				"  WHERE id_pedido = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		if (!rs.next()) return null;

		Endereco e = new Endereco();
		e.setCidade(rs.getString("nm_cidade"));
		e.setEstado(rs.getString("sg_estado"));
		e.setNomeRua(rs.getString("nm_rua"));
		Pedido p = new Pedido(rs.getInt("id_pedido"), rs.getDate("dt_pedido"),
				new ClienteDAO().getClienteByEmail(rs.getString("ds_email")),
				new Vendedor(rs.getInt("id_vendedor"), rs.getString("nm_vendedor")),
				new Loja(rs.getInt("id_loja"), e), null);
		p.setItensVenda(pesquisarItensDoPedido(p));
		return p;
	}

	public List<ItemVenda> pesquisarItensDoPedido(Pedido pedido) throws SQLException {
		sql = "SELECT id_pedido, tb_produto.id_produto, nr_quantidade qtde, vl_unitario_base, " +
				"        vl_preco_unitario preco, id_vendedor_aprovador, nm_vendedor, nm_produto, " +
				"        tb_unidade_medida.id_unidade_medida um_id, tb_unidade_medida.nm_unidade_medida um_nm" +
				"    FROM tb_produtos_venda" +
				"    INNER JOIN tb_produto" +
				"    ON tb_produto.id_produto = tb_produtos_venda.id_produto" +
				"    INNER JOIN tb_vendedor" +
				"    ON tb_vendedor.id_vendedor = tb_produtos_venda.id_vendedor_aprovador" +
				"    INNER JOIN tb_unidade_medida" +
				"    ON tb_produto.id_unidade_medida = tb_unidade_medida.id_unidade_medida" +
				"    WHERE id_pedido = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, pedido.getId());
		rs = ps.executeQuery();
		List<ItemVenda> vendas = new LinkedList<>();
		while (rs.next()) {
			ItemVenda item = new ItemVenda(
					new Produto(
							rs.getInt("id_produto"),
							rs.getString("nm_produto"),
							rs.getDouble("vl_unitario_base"),
							new UnidadeMedida(rs.getInt("um_id"), rs.getString("um_nm"))
					),
					rs.getInt("qtde"),
					rs.getDouble("preco"),
					new Vendedor(
							rs.getInt("id_vendedor_aprovador"),
							rs.getString("nm_vendedor")
					)
			);
			vendas.add(item);
		}
		return vendas;
	}

	public Pedido cadastrar(Pedido pedido) throws SQLException {
		conn.setAutoCommit(false);

		try {

			List<ItemVenda> itensVenda = pedido.getItensVenda();
			sql = "SELECT sq_id_pedido.nextval FROM dual";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			pedido.setId(rs.getInt(1));

			sql = "INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) " +
					"    VALUES(?, default, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pedido.getId());
			ps.setInt(2, pedido.getCliente().getId());
			ps.setInt(3, pedido.getVendedor().getId());
			ps.setInt(4, pedido.getLoja().getId());
			ps.execute();

			for (ItemVenda item : itensVenda) {
				sql =
						"INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, " +
								" vl_preco_unitario, id_vendedor_aprovador)" +
								" VALUES(?, ?, ?, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = ?), ?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, pedido.getId());
				ps.setInt(2, item.getProduto().getId());
				ps.setInt(3, item.getQuantidade());
				ps.setInt(4, item.getProduto().getId());
				ps.setInt(5, item.getVendedorAprovador().getId());
				ps.execute();
			}

			ps = conn.prepareStatement("COMMIT");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.prepareStatement("ROLLBACK").execute();
		}

		conn.setAutoCommit(true);
		return pedido;
	}

	public List<Pedido> listarPedidos() throws SQLException {
		List<Pedido> pedidos = new LinkedList<>();
		sql = "SELECT id_pedido, dt_pedido, tb_pedido.id_cliente, tb_pedido.id_vendedor, " +
				"    tb_pedido.id_loja, tb_loja.id_endereco, nm_vendedor, tb_cidade.nm_cidade, " +
				"    tb_endereco.sg_estado, nm_rua, nm_cliente" +
				"    FROM tb_pedido" +
				"    INNER JOIN tb_loja" +
				"    ON tb_pedido.id_loja = tb_loja.id_loja" +
				"    INNER JOIN tb_vendedor" +
				"    ON tb_pedido.id_vendedor = tb_vendedor.id_vendedor" +
				"    INNER JOIN tb_endereco" +
				"    ON tb_loja.id_endereco = tb_endereco.id_endereco" +
				"    INNER JOIN tb_cidade" +
				"    ON tb_endereco.id_cidade = tb_cidade.id_cidade" +
				"    INNER JOIN tb_cliente" +
				"    ON tb_cliente.id_cliente = tb_pedido.id_cliente";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			Endereco e = new Endereco();
			e.setCidade(rs.getString("nm_cidade"));
			e.setEstado(rs.getString("sg_estado"));
			e.setNomeRua(rs.getString("nm_rua"));
			Cliente c = new Cliente();
			c.setId(rs.getInt("id_cliente"));
			c.setNome(rs.getString("nm_cliente"));
			Pedido p = new Pedido(rs.getInt("id_pedido"), rs.getDate("dt_pedido"), c,
					new Vendedor(rs.getInt("id_vendedor"), rs.getString("nm_vendedor")),
					new Loja(rs.getInt("id_loja"), e), null);
			pedidos.add(p);
		}
		return pedidos;
	}

	public void atualizar(Pedido pedido) throws SQLException {
		conn.setAutoCommit(false);
		List<ItemVenda> itens = pedido.getItensVenda();

		try {
			for (ItemVenda item : itens) {
				sql = "SELECT * FROM tb_produtos_venda WHERE id_pedido = ? AND id_produto = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, pedido.getId());
				ps.setInt(2, item.getProduto().getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					System.out.println("Atualizando");
					System.out.println(item);
					sql = "UPDATE tb_produtos_venda" +
							"    SET nr_quantidade = ?," +
							"        vl_preco_unitario = ?" +
							"    WHERE id_pedido = ? AND id_produto = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, item.getQuantidade());
					ps.setDouble(2, item.getPreco());
					ps.setInt(3, pedido.getId());
					ps.setInt(4, item.getProduto().getId());
					ps.execute();
				} else {
					sql = "INSERT " +
							"    INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, " +
							"         vl_preco_unitario, id_vendedor_aprovador) " +
							"    VALUES(?, ?, ?, ?, ?)";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, pedido.getId());
					ps.setInt(2, item.getProduto().getId());
					ps.setInt(3, item.getQuantidade());
					ps.setDouble(4, item.getPreco());
					ps.setInt(5, pedido.getVendedor().getId());
					ps.execute();
				}
			}

			sql = "DELETE FROM tb_produtos_venda WHERE id_pedido = ?";
			if (itens.size() > 0)
				sql += " AND id_produto NOT IN (" +
						PedidoDAO.getCommaSeparatedIdProdutoValues(itens) + ")";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pedido.getId());
			ps.execute();
			conn.prepareStatement("COMMIT").execute();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.prepareStatement("ROLLBACK").execute();
		}
		conn.setAutoCommit(true);
	}

	public static String getCommaSeparatedIdProdutoValues(List<ItemVenda> itensVenda) {
		StringBuilder values = new StringBuilder();
		for (int i = 0; i < itensVenda.size(); i++) {
			values.append(itensVenda.get(i).getProduto().getId());
			if (i != (itensVenda.size() - 1))
				values.append(",");
		}
		return values.toString();
	}

	;

	public void deletar(Integer id) throws SQLException {
		conn.setAutoCommit(false);
		try {
			sql = "DELETE FROM tb_produtos_venda WHERE id_pedido = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();

			sql = "DELETE FROM tb_pedido WHERE id_pedido = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			conn.prepareStatement("COMMIT").execute();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.prepareStatement("ROLLBACK").execute();
		}
		conn.setAutoCommit(true);
	}

}
