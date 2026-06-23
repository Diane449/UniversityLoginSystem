package Controller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfDAO {
    public List<String[]> getRichiestePendenti(String usernameProf) {
        List<String[]> richieste = new ArrayList<>();
        String query = "SELECT r.id_corso, r.username_studente, c.nome_corso, u.nome AS nome_studente " +
                       "FROM richieste r JOIN corsi c ON r.id_corso = c.id_corso " +
                       "JOIN utenti u ON r.username_studente = u.username " +
                       "WHERE c.id_professore = ? AND r.stato = 'In Attesa'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, usernameProf);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                richieste.add(new String[]{
                    String.valueOf(rs.getInt("id_corso")),
                    rs.getString("username_studente"),
                    rs.getString("nome_studente"),
                    rs.getString("nome_corso")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return richieste;
    }

    public boolean approvaRichiesta(int idCorso, String usernameStudente) {
        String query = "UPDATE richieste SET stato = 'Approvato' WHERE id_corso = ? AND username_studente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idCorso);
            stmt.setString(2, usernameStudente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }
}