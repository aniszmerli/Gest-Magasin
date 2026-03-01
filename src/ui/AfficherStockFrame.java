package ui;

import model.Produit;
import services.StockService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class AfficherStockFrame extends JFrame {

    private final StockService      service    = new StockService();
    private final DefaultTableModel tableModel;

    public AfficherStockFrame() {
        setTitle("Afficher Stock");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Stock actuel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(40, 55, 71));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Nom", "Description", "Prix (€)", "Quantité"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("🔄  Actualiser");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> rafraichir());

        JPanel south = new JPanel();
        south.setBackground(new Color(245, 247, 250));
        south.setBorder(BorderFactory.createEmptyBorder(8, 0, 12, 0));
        south.add(btnRefresh);
        add(south, BorderLayout.SOUTH);

        rafraichir();
    }

    private void rafraichir() {
        tableModel.setRowCount(0);
        for (Map.Entry<Produit, Integer> entry : service.getStockMap().entrySet()) {
            Produit p = entry.getKey();
            tableModel.addRow(new Object[]{
                    p.getId(), p.getNom(), p.getDescription(), p.getPrix(), entry.getValue()
            });
        }
    }
}