package ui;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        setTitle("Gestion de Stock Magasin");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Main panel ───────────────────────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // ── Title label ──────────────────────────────────────────────────────
        JLabel titleLabel = new JLabel("Gestion de Stock", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(40, 55, 71));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ── Button grid ──────────────────────────────────────────────────────
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

        JButton btnAfficherStock  = createNavButton("📦  Afficher Stock",   new Color(52, 152, 219));
        JButton btnAjouterArticle = createNavButton("➕  Ajouter Article",  new Color(46, 204, 113));
        JButton btnAjouterStock   = createNavButton("🔄  Ajouter au Stock", new Color(155, 89, 182));
        JButton btnSettings       = createNavButton("⚙️  Paramètres",       new Color(149, 165, 166));

        btnAfficherStock.addActionListener(e  -> openAfficherStock());
        btnAjouterArticle.addActionListener(e -> openAjouterArticle());
        btnAjouterStock.addActionListener(e   -> openAjouterStock());
        btnSettings.addActionListener(e       -> openSettings());

        buttonPanel.add(btnAfficherStock);
        buttonPanel.add(btnAjouterArticle);
        buttonPanel.add(btnAjouterStock);
        buttonPanel.add(btnSettings);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    // ── Helper: styled button ────────────────────────────────────────────────
    private JButton createNavButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color original = color;
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(original.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });

        return btn;
    }

    // ── Navigation actions ───────────────────────────────────────────────────
    private void openAfficherStock() {
        new AfficherStockFrame().setVisible(true);
    }

    private void openAjouterArticle() {
        new AjouterArticleFrame().setVisible(true);
    }

    private void openAjouterStock() {
        new AjouterStockFrame().setVisible(true);
    }

    private void openSettings() {
        new SettingsFrame().setVisible(true);
    }

    // ── Entry point ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}