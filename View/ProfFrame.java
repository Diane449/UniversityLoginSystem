package View;
import javax.swing.*;

import Controller.ProfDAO;
import Model.Prof;

import java.awt.*;
import java.util.List;

public class ProfFrame extends JFrame {
    private Prof prof;
    private ProfDAO profDAO = new ProfDAO();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> listaRichieste = new JList<>(listModel);
    private List<String[]> datiRichieste;

    public ProfFrame(Prof prof) {
        this.prof = prof;
        setTitle("Dashboard Docente - " + prof.getNome());
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        JLabel lblWelcome = new JLabel("Benvenuto, " + prof.getNome(), SwingConstants.LEFT);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblInfo = new JLabel(" Richieste di ammissione in attesa:");
        northPanel.add(lblWelcome); northPanel.add(lblInfo);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(northPanel, BorderLayout.NORTH);

        listaRichieste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaRichieste.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listaRichieste);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton btnApprova = new JButton("Approva Studente");
        JButton btnLogout = new JButton("Disconnetti");
        JPanel southPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        southPanel.add(btnApprova); southPanel.add(btnLogout);
        add(southPanel, BorderLayout.SOUTH);

        caricaRichieste();

        btnApprova.addActionListener(e -> {
            int index = listaRichieste.getSelectedIndex();
            if (index != -1) {
                int idCorso = Integer.parseInt(datiRichieste.get(index)[0]);
                String usernameStudente = datiRichieste.get(index)[1];
                if (profDAO.approvaRichiesta(idCorso, usernameStudente)) {
                    JOptionPane.showMessageDialog(this, "Studente approvato!");
                    caricaRichieste();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona una richiesta.");
            }
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void caricaRichieste() {
        listModel.clear();
        datiRichieste = profDAO.getRichiestePendenti(prof.getUsername());
        if (datiRichieste.isEmpty()) {
            listModel.addElement(" Non ci sono richieste in attesa.");
        } else {
            for (String[] r : datiRichieste) {
                listModel.addElement(" Studente: " + r[2] + " | Corso: " + r[3]);
            }
        }
    }
}