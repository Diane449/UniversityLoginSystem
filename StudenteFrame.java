import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudenteFrame extends JFrame {
    private Studente studente;
    private StudenteDAO studenteDAO = new StudenteDAO();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> listaCorsi = new JList<>(listModel);
    private List<String[]> datiCorsi;

    public StudenteFrame(Studente studente) {
        this.studente = studente;
        setTitle("Dashboard Studente - " + studente.getNome());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        JLabel lblWelcome = new JLabel("Benvenuto, " + studente.getNome(), SwingConstants.LEFT);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblInfo = new JLabel(" Elenco corsi e stato della tua iscrizione:");
        northPanel.add(lblWelcome); northPanel.add(lblInfo);
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(northPanel, BorderLayout.NORTH);

        listaCorsi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCorsi.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listaCorsi);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton btnRichiedi = new JButton("Invia Richiesta Iscrizione");
        JButton btnLogout = new JButton("Disconnetti");
        JPanel southPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        southPanel.add(btnRichiedi); southPanel.add(btnLogout);
        add(southPanel, BorderLayout.SOUTH);

        caricaCorsi();

        btnRichiedi.addActionListener(e -> {
            int index = listaCorsi.getSelectedIndex();
            if (index != -1) {
                int idCorso = Integer.parseInt(datiCorsi.get(index)[0]);
                String nomeCorso = datiCorsi.get(index)[1];
                String statoAttuale = datiCorsi.get(index)[3];

                if (!"Disponibile".equalsIgnoreCase(statoAttuale)) {
                    JOptionPane.showMessageDialog(this, "Richiesta già effettuata o sei già iscritto.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (studenteDAO.inviaRichiesta(studente.getUsername(), idCorso)) {
                    JOptionPane.showMessageDialog(this, "Richiesta inviata per: " + nomeCorso);
                    caricaCorsi();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un corso.");
            }
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void caricaCorsi() {
        listModel.clear();
        datiCorsi = studenteDAO.getCorsiEStato(studente.getUsername());
        for (String[] c : datiCorsi) {
            String riga = String.format(" Corso: %-22s | Docente: %-18s | Stato: [%s]", 
                            c[1], c[2], c[3].toUpperCase());
            listModel.addElement(riga);
        }
    }
}