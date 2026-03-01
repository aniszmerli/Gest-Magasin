package ui;

import services.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginScreen extends JFrame {

    private final JTextField     txtUser = new JTextField(18);
    private final JPasswordField txtPass = new JPasswordField(18);

    public LoginScreen() {
        setTitle("Connexion");
        setSize(360, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JLabel icon  = new JLabel("🏪", SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.PLAIN, 36));
        icon.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        JLabel title = new JLabel("Gestion de Stock", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(40, 55, 71));
        title.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setBackground(new Color(245, 247, 250));
        top.add(icon);
        top.add(title);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        form.add(styledLabel("Utilisateur :"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtUser.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(styledLabel("Mot de passe :"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtPass.setFont(new Font("Arial", Font.PLAIN, 13));
        form.add(txtPass, gbc);

        add(form, BorderLayout.CENTER);

        JButton btnLogin = new JButton("Se connecter");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> tenterConnexion());
        txtPass.addActionListener(e -> tenterConnexion());

        JPanel south = new JPanel();
        south.setBackground(new Color(245, 247, 250));
        south.setBorder(BorderFactory.createEmptyBorder(4, 0, 16, 0));
        south.add(btnLogin);
        add(south, BorderLayout.SOUTH);
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        return l;
    }

    private void tenterConnexion() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());

        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dispose();
                new HomeScreen().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Identifiants incorrects. Réessayez.",
                        "Échec de connexion", JOptionPane.ERROR_MESSAGE);
                txtPass.setText("");
                txtPass.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion à la base de données :\n" + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}