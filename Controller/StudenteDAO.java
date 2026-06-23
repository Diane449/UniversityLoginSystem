package Controller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudenteDAO {
    public List<String[]> getCorsiEStato(String usernameStudente) {
        List<String[]> corsi = new ArrayList<>();
        String query = "SELECT c.id_corso, c.nome_corso, p.nome AS nome_prof, r.stato " +
                       "FROM corsi c JOIN utenti p ON c.id_professore = p.username " +
                       "LEFT JOIN richieste r ON c.id_corso = r.id_corso AND r.username_studente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, usernameStudente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String stato = rs.getString("stato");
                if (stato == null) stato = "Disponibile";
                corsi.add(new String[]{
                    String.valueOf(rs.getInt("id_corso")), rs.getString("nome_corso"), rs.getString("nome_prof"), stato
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return corsi;
    }

    public boolean inviaRichiesta(String usernameStudente, int idCorso) {
        String query = "INSERT INTO richieste (id_corso, username_studente, stato) VALUES (?, ?, 'In Attesa')";
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