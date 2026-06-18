public class Corso {
    private int idCorso;
    private String nomeCorso;
    private Prof docente;

    public Corso(int idCorso, String nomeCorso, Prof docente) {
        this.idCorso = idCorso;
        this.nomeCorso = nomeCorso;
        this.docente = docente;
    }

    // Getter e Setter
    public int getIdCorso() { return idCorso; }
    public void setIdCorso(int idCorso) { this.idCorso = idCorso; }

    public String getNomeCorso() { return nomeCorso; }
    public void setNomeCorso(String nomeCorso) { this.nomeCorso = nomeCorso; }

    public Prof getDocente() { return docente; }
    public void setDocente(Prof docente) { this.docente = docente; }
}