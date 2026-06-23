package Controller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Corso;
import Model.Prof;

public class CorsoDAO {

    // Recupera la lista di tutti i corsi presenti nel sistema come oggetti "Corso" reali
    public List<Corso> getAllCorsi() {
        List<Corso> corsi = new ArrayList<>();
        String query = "SELECT c.id_corso, c.nome_corso, u.username, u.nome " +
                       "FROM corsi c JOIN utenti u ON c.id_professore = u.username";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Ricostruiamo l'oggetto Professore associato al corso
                Prof prof = new Prof(rs.getString("username"), rs.getString("nome"));
                
                // Creiamo l'oggetto Corso
                Corso corso = new Corso(rs.getInt("id_corso"), rs.getString("nome_corso"), prof);
                corsi.add(corso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corsi;
    }
}