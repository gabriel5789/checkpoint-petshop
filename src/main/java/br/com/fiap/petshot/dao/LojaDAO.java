package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.Loja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LojaDAO {

	private PreparedStatement ps;
	private ResultSet rs;
	private final Connection conn;
	private String sql = "";

	public LojaDAO() {
		conn = Conexao.conectar();
	}

	public List<Loja> listarLojas() throws SQLException {
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		sql = "SELECT * FROM tb_loja";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		List<Loja> Lojas = new LinkedList<>();
		while (rs.next()) {
			Lojas.add(new Loja(rs.getInt("id_Loja"), enderecoDAO.getEndereco(rs.getInt("id_endereco"))));
		}
		return Lojas;
	}

	public Loja pesquisar(Integer id) throws SQLException {
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		sql = "SELECT * FROM tb_Loja WHERE id_Loja = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		if (!rs.next()) return null;
		return new Loja(rs.getInt("id_Loja"), enderecoDAO.getEndereco(rs.getInt("id_endereco")));
	}
}
