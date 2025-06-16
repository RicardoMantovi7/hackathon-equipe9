package felipe.nascimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe base para todos os DAOs (Data Access Objects).
 * Responsável por abrir a conexão com o banco de dados.
 */
public class Dao {
    // Constantes com os dados de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/javapoo?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    // Guarda a conexão ativa
    private Connection connection;

    /**
     * O construtor tenta se conectar ao banco de dados assim que um DAO é criado.
     */
    public Dao() {
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para que as outras classes possam obter a conexão ativa.
     * @return A conexão com o banco de dados.
     */
    public Connection getConnection() {
        return connection;
    }
}