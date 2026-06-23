package Model;
public abstract class Persona {
    private String username;
    private String nome;
    private String ruolo;

    public Persona(String username, String nome, String ruolo) {
        this.username = username;
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getRuolo() { return ruolo; }
}