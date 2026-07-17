package Model;

public class Iscrizione {
    private Studente studente;
    private Corso corso;
    private String stato; // "PENDING", "ACCETTATO", "RIFIUTATO"

    public Iscrizione(Studente s, Corso c, String stato) {
        this.studente = s;
        this.corso = c;
        this.stato = stato;
    }
    // Getter...
    public Studente getStudente() { return studente; }
    public String getStato() { return stato;}
}
