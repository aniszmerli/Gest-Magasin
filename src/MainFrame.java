
import model.Produit;
import services.StockService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame {
    private StockService service;
    private JTable table;
    private DefaultTableModel tableModel;

    // Champs de formulaire
    private JTextField txtNom = new JTextField(10);
    private JTextField txtDesc = new JTextField(10);
    private JTextField txtPrix = new JTextField(5);
    private JTextField txtQte = new JTextField(5);

    public MainFrame() {
        service = new StockService();

        setTitle("Gestion de Stock Magasin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Création du Tableau (Centre)
        String[] colonnes = {"ID", "Nom", "Description", "Prix", "Quantité"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 2. Création du Formulaire (Bas)
        JPanel panelBas = new JPanel();
        panelBas.setLayout(new FlowLayout());

        panelBas.add(new JLabel("Nom:"));
        panelBas.add(txtNom);
        panelBas.add(new JLabel("Desc:"));
        panelBas.add(txtDesc);
        panelBas.add(new JLabel("Prix:"));
        panelBas.add(txtPrix);
        panelBas.add(new JLabel("Qté:"));
        panelBas.add(txtQte);

        JButton btnAjouter = new JButton("Ajouter");
        // Action du bouton Ajouter
        btnAjouter.addActionListener(e -> ajouterProduitAction());
        panelBas.add(btnAjouter);

        add(panelBas, BorderLayout.SOUTH);

        // Chargement initial des données
        rafraichirTableau();
    }

    private void rafraichirTableau() {
        // Vider le tableau
        tableModel.setRowCount(0);

        // Remplir avec les données de la Map du service
        for (Map.Entry<Produit, Integer> entry : service.getStockMap().entrySet()) {
            Produit p = entry.getKey();
            Integer qte = entry.getValue();

            Object[] ligne = {
                    p.getId(),
                    p.getNom(),
                    p.getDescription(),
                    p.getPrix(),
                    qte
            };
            tableModel.addRow(ligne);
        }
    }

    private void ajouterProduitAction() {
        try {
            String nom = txtNom.getText();
            String desc = txtDesc.getText();
            double prix = Double.parseDouble(txtPrix.getText());
            int qte = Integer.parseInt(txtQte.getText());

            service.ajouterProduit(nom, desc, prix, qte);
            rafraichirTableau();

            // Vider les champs
            txtNom.setText("");
            txtDesc.setText("");
            txtPrix.setText("");
            txtQte.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erreur : Vérifiez les nombres (prix/qté).");
        }
    }

    public static void main(String[] args) {
        // Lancer l'interface
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}