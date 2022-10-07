package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.Produto;
import br.com.fiap.petshot.model.UnidadeMedida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProdutoDAO {
    private PreparedStatement ps;
    private ResultSet rs;
    private final Connection conn;
    private String sql = "";

    public ProdutoDAO() {
        conn = Conexao.conectar();
    }

    public Produto cadastrar(Produto produto) throws SQLException {
        if(pesquisar(produto.getId()) != null || pesquisarPorNome(produto.getNome()) != null)
            return produto;
        sql = "SELECT sq_id_produto.nextval FROM dual";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        produto.setId(rs.getInt(1));

        sql = "INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) " +
                "VALUES(?, ?, ?, (SELECT id_unidade_medida FROM tb_unidade_medida WHERE nm_unidade_medida = ?))";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, produto.getId());
        ps.setString(2, produto.getNome());
        ps.setDouble(3, produto.getValor());
        ps.setString(4, produto.getUnidadeMedida().getDesc());
        ps.execute();
        return produto;
    }

    public Produto pesquisarPorNome(String nomeProduto) throws SQLException {
        if(nomeProduto == null) return null;
        sql = "SELECT id_produto, nm_produto, vl_unitario_base, tb_produto.id_unidade_medida as id_un, " +
                "nm_unidade_medida as ds_un FROM TB_PRODUTO" +
                " inner join tb_unidade_medida" +
                " on TB_PRODUTO.ID_UNIDADE_MEDIDA = TB_UNIDADE_MEDIDA.id_unidade_medida WHERE nm_produto = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nomeProduto);
        rs = ps.executeQuery();
        if(!rs.next()) return null;
        return new Produto(rs.getInt("id_produto"), rs.getString("nm_produto"),
                rs.getDouble("vl_unitario_base"),
                new UnidadeMedida(rs.getInt("id_un"), rs.getString("ds_un")));
    }

    public Produto pesquisar(Integer idProduto) throws SQLException {
        if(idProduto == null) return null;
        sql = "SELECT id_produto, nm_produto, vl_unitario_base, tb_produto.id_unidade_medida as id_un, " +
                "nm_unidade_medida as ds_un FROM TB_PRODUTO" +
                " inner join tb_unidade_medida" +
                " on TB_PRODUTO.ID_UNIDADE_MEDIDA = TB_UNIDADE_MEDIDA.id_unidade_medida WHERE id_produto = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, idProduto);
        rs = ps.executeQuery();
        if(!rs.next()) return null;
        return new Produto(rs.getInt("id_produto"), rs.getString("nm_produto"),
                rs.getDouble("vl_unitario_base"),
                new UnidadeMedida(rs.getInt("id_un"), rs.getString("ds_un")));
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new LinkedList<>();

        sql = "SELECT id_produto, nm_produto, vl_unitario_base, tb_produto.id_unidade_medida as id_un, " +
                "nm_unidade_medida as ds_un FROM TB_PRODUTO" +
            " inner join tb_unidade_medida" +
            " on TB_PRODUTO.ID_UNIDADE_MEDIDA = TB_UNIDADE_MEDIDA.id_unidade_medida";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while(rs.next()) {
            produtos.add(
                    new Produto(rs.getInt("id_produto"), rs.getString("nm_produto"),
                    rs.getDouble("vl_unitario_base"),
                    new UnidadeMedida(rs.getInt("id_un"), rs.getString("ds_un")))
            );
        }
        return produtos;
    }

    public List<UnidadeMedida> getUnidadesDeMedida() {
        sql = "SELECT id_unidade_medida, nm_unidade_medida FROM tb_unidade_medida";
        List<UnidadeMedida> output = new LinkedList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                output.add(new UnidadeMedida(rs.getInt("id_unidade_medida"),
                        rs.getString("nm_unidade_medida")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void atualizar(Produto produto) throws SQLException {
        sql = "UPDATE tb_produto SET " +
                "nm_produto = ?, " +
                "vl_unitario_base = ?, " +
                "id_unidade_medida = (SELECT id_unidade_medida FROM tb_unidade_medida WHERE nm_unidade_medida = ?) " +
                "WHERE id_produto = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ps.setDouble(2, produto.getValor());
        ps.setString(3, produto.getUnidadeMedida().getDesc());
        ps.setInt(4, produto.getId());
        ps.execute();
    }

    public void deletar(Integer id) throws SQLException {
        sql = "DELETE FROM tb_produto WHERE id_produto = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
