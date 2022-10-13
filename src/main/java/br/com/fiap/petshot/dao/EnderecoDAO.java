package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EnderecoDAO {
	private PreparedStatement ps;
	private ResultSet rs;
	private final Connection conn;
	private String sql = "";

	public EnderecoDAO() {
		conn = Conexao.conectar();
	}

	public List<String> getEstados() throws SQLException {
		sql = "SELECT sg_estado FROM tb_estado";
		List<String> estados = new LinkedList<>();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			estados.add(rs.getString("sg_estado"));
		}
		return estados;
	}

	public List<String> getCidades(String siglaEstado) throws SQLException {
		sql = "SELECT nm_cidade FROM tb_cidade WHERE sg_estado = ?";
		List<String> cidades = new LinkedList<>();
		ps = conn.prepareStatement(sql);
		ps.setString(1, siglaEstado.toUpperCase());
		rs = ps.executeQuery();
		while (rs.next()) {
			cidades.add(rs.getString("nm_cidade"));
		}
		return cidades;
	}

	public String getNomeCidade(Integer idCidade) throws SQLException {
		sql = "SELECT nm_cidade FROM tb_cidade WHERE id_cidade = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, idCidade);
		rs = ps.executeQuery();
		if (rs.next())
			return rs.getString("nm_cidade");
		else return null;
	}

	public Endereco getEndereco(Integer idEndereco) throws SQLException {
		sql = "SELECT * FROM tb_endereco WHERE id_endereco = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, idEndereco);
		rs = ps.executeQuery();

		Endereco endereco = null;
		if (rs.next()) {
			endereco = new Endereco(
					idEndereco, rs.getString("nm_rua"), rs.getInt("nr_rua"),
					rs.getString("ds_complemento"), rs.getInt("nr_cep"),
					rs.getString("nm_bairro"), "", rs.getString("sg_estado")
			);
			endereco.setCidade(getNomeCidade(rs.getInt("id_cidade")));
		}
		return endereco;
	}

	public void delete(Integer id) throws SQLException {
		sql = "DELETE FROM tb_endereco WHERE id_endereco = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.execute();
	}

	public void atualizar(Endereco endereco) throws SQLException {
		sql = "UPDATE tb_endereco" +
				"    SET nm_rua = ?," +
				"        nr_rua = ?," +
				"        ds_complemento = ?," +
				"        nr_cep = ?," +
				"        nm_bairro = ?," +
				"        id_cidade = (SELECT id_cidade FROM tb_cidade WHERE nm_cidade = ?)," +
				"        sg_estado = ?" +
				"    WHERE id_endereco = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, endereco.getNomeRua());
		ps.setInt(2, endereco.getNrRua());
		ps.setString(3, endereco.getComplemento());
		ps.setInt(4, endereco.getCep());
		ps.setString(5, endereco.getBairro());
		ps.setString(6, endereco.getCidade());
		ps.setString(7, endereco.getEstado());
		ps.setInt(8, endereco.getId());
		ps.executeUpdate();
	}

	public Endereco save(Endereco endereco) throws SQLException {
		sql = "SELECT sq_id_endereco.nextval FROM dual";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		endereco.setId(id);
		sql =
				"INSERT INTO " +
						"tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado) " +
						"VALUES (" +
						"?, ?, ?, ?, ?, ?, " +
						"(SELECT id_cidade FROM tb_cidade WHERE nm_cidade = ? AND sg_estado = ?), ?" +
						")";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, endereco.getNomeRua());
		ps.setInt(3, endereco.getNrRua());
		ps.setString(4, endereco.getComplemento());
		ps.setInt(5, endereco.getCep());
		ps.setString(6, endereco.getBairro());
		ps.setString(7, endereco.getCidade());
		ps.setString(8, endereco.getEstado());
		ps.setString(9, endereco.getEstado());
		ps.execute();
		return endereco;
	}
}
