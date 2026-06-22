import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfDAO {

    // 🌟 NUOVO METODO PER IL LOGIN DEL PROFESSORE
    public Prof login(String username, String password) {
        // Modifica 'utenti' con il nome della tua tabella (es. 'professori' se ne hai una separata)
        String query = "SELECT username, nome FROM utenti WHERE username = ? AND password = ? AND ruolo = 'Professore'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Se lo trova, crea e restituisce l'oggetto Prof
                return new Prof(rs.getString("username"), rs.getString("nome"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return null; // Se le credenziali sono sbagliate o il prof non esiste
    }

    // Metodo per le richieste (Ho corretto la query presupponendo che c.id_professore sia l'username. 
    // Se invece usi un ID numerico, dovrai passare l'ID intero del prof invece dell'username!)
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
