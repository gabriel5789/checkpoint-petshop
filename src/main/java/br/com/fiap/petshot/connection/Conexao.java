package br.com.fiap.petshot.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String user = "";
    private static final String password = "";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String path = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    private static Connection conn;

    public static Connection conectar() {
        try {
            if (conn != null && !conn.isClosed())
                return conn;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(path, user, password);
        }
        catch(ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver de conexão\n"+e);
        }
        catch(SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados\n"+e);
        }

        return conn;
    }

    public static void fechar() {
        try {
            if(conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
