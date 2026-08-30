package FuncionarioRepository;

import ConnectionDataBase.DatabaseConnection;
import funcionarios.Funcionarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FuncionarioRepository {

    private static final Logger log = Logger.getLogger(FuncionarioRepository.class.getName());

    // INSERT
    public void save(Funcionarios funcionarios) throws SQLException {
        String sql = "INSERT INTO funcionarios (primeiro_nome, ultimo_nome, pagamento_Hora, hire_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionarios.getPrimeiroNome());
            stmt.setString(2, funcionarios.getUltimoNome());
            stmt.setDouble(3, funcionarios.getPagamentoHora());
            stmt.setString(4, funcionarios.getHireDate());
            stmt.executeUpdate();

            log.info("Funcionário salvo com sucesso!");
        }
    }

    // SELECT ALL
    public List<Funcionarios> findAll() throws SQLException {
        String sql = "SELECT * FROM funcionarios";
        List<Funcionarios> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcionarios e = new Funcionarios(
                        rs.getString("primeiro_nome"),
                        rs.getString("ultimo_nome"),
                        rs.getDouble("pagamento_Hora"),
                        rs.getString("hire_date")
                );
                e.setFuncionario_id(rs.getInt("funcionario_id"));
                lista.add(e);
            }
        }
        return lista;
    }

    // SELECT BY ID
    public Funcionarios findById(int id) throws SQLException {
        String sql = "SELECT * FROM funcionarios WHERE funcionario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Funcionarios e = new Funcionarios(
                        rs.getString("primeiro_nome"),
                        rs.getString("ultimo_nome"),
                        rs.getDouble("pagamento_Hora"),
                        rs.getString("hire_date")
                );
                e.setFuncionario_id(rs.getInt("funcionario_id"));
                return e;
            }
        }
        return null;
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE funcionario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) log.info("Funcionário deletado com sucesso!");
            log.info("Funcionário não encontrado!");
        }
    }
}





