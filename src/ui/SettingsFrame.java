package ui;

import services.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

/**
 * Settings screen — lets the user update database connection parameters at runtime.
 * Changes are applied to DatabaseConnection and tested immediately.
 */
public class SettingsFrame extends JFrame {

    private final JTextField txtHost = new JTextField(20);
    private final JTextField txtPort = new JTextField(6);
    private final JTextField txtDB   = new JTextField(20);
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPass = new JPasswordField(20);

    public SettingsFrame() {
        setTitle("Paramètres");
        setSize(440, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // ── Title ────────────────────────────────────────────────────────────
        JLabel title = new JLabel("Paramètres de connexion", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(40, 55, 71));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);



        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        addFormRow(form, gbc, 0, "Hôte :",      txtHost);
        addFormRow(form, gbc, 1, "Port :",       txtPort);
        addFormRow(form, gbc, 2, "Base de données :", txtDB);
        addFormRow(form, gbc, 3, "Utilisateur :", txtUser);
        addFormRow(form, gbc, 4, "Mot de passe :", txtPass);
        add(form, BorderLayout.CENTER);



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


}