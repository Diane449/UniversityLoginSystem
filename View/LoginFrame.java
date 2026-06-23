package View;
import javax.swing.*;

import Controller.AuthDAO;
import Model.Persona;
import Model.Prof;
import Model.Studente;
//import View.ProfFrame;

import java.awt.*;

public class LoginFrame extends JFrame {
    private AuthDAO authDAO = new AuthDAO();
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Accesso al Portale Universitario");
        setSize(380, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Benvenuto, effettua il login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitle);

        txtUser = new JTextField();
        txtUser.setBorder(BorderFactory.createTitledBorder("Username")); 
        add(txtUser);

        txtPass = new JPasswordField();
        txtPass.setBorder(BorderFactory.createTitledBorder("Password"));
        add(txtPass);

        btnLogin = new JButton("Accedi");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnLogin);

        btnLogin.addActionListener(e -> eseguiLogin());
    }

    private void eseguiLogin() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.", "Avviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Persona utente = authDAO.login(username, password);

        if (utente != null) {
            this.dispose(); 
            if (utente instanceof Studente) {
                new StudenteFrame((Studente) utente).setVisible(true);
            } else if (utente instanceof Prof) {
                new ProfFrame((Prof) utente).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
            txtPass.setText("");
        }
    }
}