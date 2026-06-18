import java.sql.*;

public class AuthDAO {
    public Persona login(String username, String password) {
        String query = "SELECT * FROM utenti WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String ruolo = rs.getString("ruolo");
                String nome = rs.getString("nome");
                
                if ("Studente".equalsIgnoreCase(ruolo)) {
                    return new Studente(username, nome);
                } else if ("Prof".equalsIgnoreCase(ruolo)) {
                    return new Prof(username, nome);
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return null;
    }
}
