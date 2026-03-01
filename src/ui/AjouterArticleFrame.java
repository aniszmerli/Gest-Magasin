package ui;

import services.StockService;

import javax.swing.*;
import java.awt.*;


public class AjouterArticleFrame extends JFrame {

    private final StockService service = new StockService();

    private final JTextField txtNom   = new JTextField(20);
    private final JTextField txtDesc  = new JTextField(20);
    private final JTextField txtPrix  = new JTextField(10);
    private final JTextField txtQte   = new JTextField(10);

    public AjouterArticleFrame() {
        setTitle("Ajouter un Article");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Nouvel Article", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(40, 55, 71));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 8, 8, 8);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        addFormRow(form, gbc, 0, "Nom :",         txtNom);
        addFormRow(form, gbc, 1, "Description :", txtDesc);
        addFormRow(form, gbc, 2, "Prix (€) :",    txtPrix);
        addFormRow(form, gbc, 3, "Quantité :",    txtQte);
        add(form, BorderLayout.CENTER);

        JButton btnAjouter = new JButton("➕  Ajouter l'article");
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 14));
        btnAjouter.setBackground(new Color(46, 204, 113));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFocusPainted(false);
        btnAjouter.setBorderPainted(false);
        btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAjouter.addActionListener(e -> soumettre());

        JPanel south = new JPanel();
        south.setBackground(new Color(245, 247, 250));
        south.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        south.add(btnAjouter);
        add(south, BorderLayout.SOUTH);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc,
                            int row, String labelText, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(field, gbc);
    }

    private void soumettre() {
        try {
            String nom  = txtNom.getText().trim();
            String desc = txtDesc.getText().trim();
            double prix = Double.parseDouble(txtPrix.getText().trim());
            int    qte  = Integer.parseInt(txtQte.getText().trim());

            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom ne peut pas être vide.");
                return;
            }

            service.ajouterProduit(nom, desc, prix, qte);
            JOptionPane.showMessageDialog(this,
                    "Article \"" + nom + "\" ajouté avec succès !",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            txtNom.setText(""); txtDesc.setText("");
            txtPrix.setText(""); txtQte.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur : Vérifiez les valeurs numériques (prix / qté).",
                    "Saisie invalide", JOptionPane.ERROR_MESSAGE);
        }
    }
}