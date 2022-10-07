package br.com.fiap.petshot.dao;

import br.com.fiap.petshot.connection.Conexao;
import br.com.fiap.petshot.model.Cliente;
import br.com.fiap.petshot.model.ClientePF;
import br.com.fiap.petshot.model.ClientePJ;
import br.com.fiap.petshot.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClienteDAO {
    private PreparedStatement ps;
    private ResultSet rs;
    private final Connection conn;
    private String sql = "";

    public ClienteDAO() {
        conn = Conexao.conectar();
    }

    public ClientePF getClienteByCpf(Long cpf) throws SQLException {
        sql = "SELECT id_cliente FROM tb_cliente_pf WHERE nr_cpf = ?";
        ps = conn.prepareStatement(sql);
        ps.setLong(1, cpf);
        rs = ps.executeQuery();

        if (!rs.next()) return null;

        sql = "SELECT * FROM tb_cliente WHERE id_cliente = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, rs.getInt("id_cliente"));
        rs = ps.executeQuery();

        if(!rs.next()) return null;

        return new ClientePF(
                rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                rs.getString("ds_email"), cpf
        );
    }

    public ClientePJ getClienteByCnpj(Long cnpj) throws SQLException {
        sql = "SELECT id_cliente FROM tb_cliente_pj WHERE nr_cnpj = ?";
        ps = conn.prepareStatement(sql);
        ps.setLong(1, cnpj);
        rs = ps.executeQuery();

        if (!rs.next()) return null;

        sql = "SELECT * FROM tb_cliente WHERE id_cliente = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, rs.getInt("id_cliente"));
        rs = ps.executeQuery();

        if(!rs.next()) return null;

        return new ClientePJ(
                rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                rs.getString("ds_email"), cnpj
        );
    }

    public List<ClientePF> getClientesPF() throws SQLException {
        List<ClientePF> clientes = new LinkedList<>();
        sql = "SELECT * FROM tb_cliente WHERE ds_tipo_cliente = 'PF'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()) {
            clientes.add(new ClientePF(rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                    new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                    rs.getString("ds_email"), null));
        }

        for (ClientePF cli : clientes) {
            sql = "SELECT nr_cpf FROM tb_cliente_pf WHERE id_cliente = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cli.getId());
            rs = ps.executeQuery();
            rs.next();
            cli.setCpf(rs.getLong("nr_cpf"));
        }
        return clientes;
    }

    public List<ClientePJ> getClientesPJ() throws SQLException {
        List<ClientePJ> clientes = new LinkedList<>();
        sql = "SELECT * FROM tb_cliente WHERE ds_tipo_cliente = 'PJ'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()) {
            clientes.add(new ClientePJ(rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                    new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                    rs.getString("ds_email"), null));
        }

        for (ClientePJ cli : clientes) {
            sql = "SELECT nr_cnpj FROM tb_cliente_pj WHERE id_cliente = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cli.getId());
            rs = ps.executeQuery();
            rs.next();
            cli.setCnpj(rs.getLong("nr_cnpj"));
        }
        return clientes;
    }

    public void cadastrarCliente(Cliente cliente) throws SQLException{
        if(cliente.getTipoCliente().equals("PF"))
            if(this.getClienteByCpf(((ClientePF) cliente).getCpf()) != null) return;
        if(cliente.getTipoCliente().equals("PJ"))
            if(this.getClienteByCnpj(((ClientePJ) cliente).getCnpj()) != null) return;

        sql = "SELECT sq_id_cliente.nextval FROM dual";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        cliente.setId(rs.getInt(1));
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        cliente.setEndereco(enderecoDAO.save(cliente.getEndereco()));

        sql = "INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) " +
                "VALUES(?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, cliente.getId());
        ps.setString(2, cliente.getNome());
        ps.setInt(3, cliente.getEndereco().getId());
        ps.setString(4, cliente.getEmail());
        ps.setString(5, cliente.getTipoCliente());
        ps.execute();

        if(cliente.getTipoCliente().equals("PF")) {
            sql = "INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, ((ClientePF) cliente).getCpf());
            ps.setInt(2, cliente.getId());
            ps.execute();
        } else if (cliente.getTipoCliente().equals("PJ")) {
            sql = "INSERT INTO tb_cliente_pj(nr_cnpj, id_cliente) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, ((ClientePJ) cliente).getCnpj());
            ps.setInt(2, cliente.getId());
            ps.execute();
        }

        ps = conn.prepareStatement("COMMIT");
        ps.execute();
    }

    public void atualizar(Cliente cliente) throws SQLException {
        if(cliente.getTipoCliente().equals("PF")) {
            ClientePF clDB = this.getClienteByCpf(((ClientePF) cliente).getCpf());
            if(clDB == null) return;
            cliente.setId(clDB.getId());
        }
        if(cliente.getTipoCliente().equals("PJ")) {
            ClientePJ clDB = this.getClienteByCnpj(((ClientePJ) cliente).getCnpj());
            if(clDB == null) return;
            cliente.setId(clDB.getId());
        }

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        enderecoDAO.atualizar(cliente.getEndereco());

        sql = "UPDATE tb_cliente SET " +
                "nm_cliente = ?," +
                "ds_email = ?, " +
                "id_endereco = ? " +
                "WHERE id_cliente = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, cliente.getNome());
        ps.setString(2, cliente.getEmail());
        ps.setInt(3, cliente.getEndereco().getId());
        ps.setInt(4, cliente.getId());
        ps.execute();

        if(cliente.getTipoCliente().equals("PF")) {
            sql = "UPDATE tb_cliente_pf SET nr_cpf = ? WHERE id_cliente = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, ((ClientePF) cliente).getCpf());
            ps.setInt(2, cliente.getId());
        } else {
            sql = "UPDATE tb_cliente_pj SET nr_cnpj = ? WHERE id_cliente = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, ((ClientePJ) cliente).getCnpj());
            ps.setInt(2, cliente.getId());
        }
        ps.execute();
    }

    public Cliente getClienteByEmail(String email) throws SQLException {

        sql = "SELECT * FROM tb_cliente WHERE ds_email = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, email);
        rs = ps.executeQuery();

        if(!rs.next()) return null;

        return new Cliente(
                rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                rs.getString("ds_email"), rs.getString("ds_tipo_cliente")
        );
    }

    public ClientePF getClientePF(Integer id) throws SQLException {
        sql = "select * from tb_cliente" +
                "    INNER JOIN tb_cliente_pf " +
                "    ON tb_cliente.id_cliente = tb_cliente_pf.id_cliente " +
                "WHERE tb_cliente.id_cliente = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(!rs.next())
            return null;

        return new ClientePF(
                rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                rs.getString("ds_email"), rs.getLong("nr_cpf")
        );
    }
    public ClientePJ getClientePJ(Integer id) throws SQLException {
        sql = "select * from tb_cliente" +
                "    INNER JOIN tb_cliente_pj " +
                "    ON tb_cliente.id_cliente = tb_cliente_pj.id_cliente " +
                "WHERE tb_cliente.id_cliente = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(!rs.next())
            return null;

        return new ClientePJ(
                rs.getInt("id_cliente"), rs.getString("nm_cliente"),
                new EnderecoDAO().getEndereco(rs.getInt("id_endereco")),
                rs.getString("ds_email"), rs.getLong("nr_cnpj")
        );
    }

    public void remover(Integer id) throws SQLException {
        sql = "SELECT ds_tipo_cliente FROM tb_cliente WHERE id_cliente = ?";
        conn.setAutoCommit(false);
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if(!rs.next()) return;
        String tipo = rs.getString(1);
        if(tipo.equals("PF")) {
            sql = "DELETE FROM tb_cliente_pf WHERE id_cliente = ?";
        } else {
            sql = "DELETE FROM tb_cliente_pj WHERE id_cliente = ?";
        }
        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        try {
            ps.execute();
            sql = "DELETE FROM tb_cliente WHERE id_cliente = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            conn.prepareStatement("COMMIT").execute();
        } catch (SQLException e) {
            conn.prepareStatement("ROLLBACK").execute();
            throw e;
        }
        conn.setAutoCommit(true);
    }
}
