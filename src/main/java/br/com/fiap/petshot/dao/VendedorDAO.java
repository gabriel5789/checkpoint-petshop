package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VendedorDAO {
    private PreparedStatement ps;
    private ResultSet rs;
    private final Connection conn;
    private String sql = "";

    public VendedorDAO() {
        conn = Conexao.conectar();
    }

    public List<Vendedor> listarVendedores() throws SQLException {
        sql = "SELECT * FROM tb_vendedor";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        List<Vendedor> vendedores = new LinkedList<>();
        while (rs.next()) {
            vendedores.add(new Vendedor(rs.getInt("id_vendedor"), rs.getString("nm_vendedor")));
        }
        return vendedores;
    }

    public Vendedor pesquisar(Integer id) throws SQLException {
        sql = "SELECT * FROM tb_vendedor WHERE id_vendedor = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(!rs.next()) return null;
        return new Vendedor(rs.getInt("id_vendedor"), rs.getString("nm_vendedor"));
    }
}
