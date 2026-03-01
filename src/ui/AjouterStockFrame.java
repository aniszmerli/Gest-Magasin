package ui;

import model.Produit;
import services.StockService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AjouterStockFrame extends JFrame {

    private final StockService    service  = new StockService();
    private final JComboBox<String> cboProduits = new JComboBox<>();
    private final JTextField      txtQte   = new JTextField(10);
    private final List<Produit>   produits = new ArrayList<>();

    public AjouterStockFrame() {
        setTitle("Ajouter au Stock");
        setSize(400, 260);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Ajouter au Stock", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(40, 55, 71));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblProduit = new JLabel("Produit :");
        lblProduit.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(lblProduit, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        cboProduits.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(cboProduits, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblQte = new JLabel("Quantité à ajouter :");
        lblQte.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(lblQte, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        txtQte.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(txtQte, gbc);

        add(form, BorderLayout.CENTER);

        JButton btnAjouter = new JButton("🔄  Mettre à jour le stock");
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 13));
        btnAjouter.setBackground(new Color(155, 89, 182));
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

        chargerProduits();
    }

    private void chargerProduits() {
        cboProduits.removeAllItems();
        produits.clear();
        for (Map.Entry<Produit, Integer> entry : service.getStockMap().entrySet()) {
            Produit p = entry.getKey();
            produits.add(p);
            cboProduits.addItem(p.getId() + " – " + p.getNom()
                    + "  (stock actuel : " + entry.getValue() + ")");
        }
    }

    private void soumettre() {
        if (produits.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun produit disponible.");
            return;
        }
        try {
            int qteAjout = Integer.parseInt(txtQte.getText().trim());
            if (qteAjout <= 0) throw new NumberFormatException();

            Produit selected = produits.get(cboProduits.getSelectedIndex());
            int ancienneQte  = service.getStockMap().get(selected);

            service.ajouterQuantite(selected.getId(), ancienneQte + qteAjout);

            JOptionPane.showMessageDialog(this,
                    "Stock mis à jour pour \"" + selected.getNom() + "\".\n"
                            + "Nouvelle quantité : " + (ancienneQte + qteAjout),
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

            txtQte.setText("");
            chargerProduits();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur : Entrez un nombre entier positif.",
                    "Saisie invalide", JOptionPane.ERROR_MESSAGE);
        }
    }
}