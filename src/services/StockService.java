package services;

import model.Produit;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StockService {
    // La Map qui lie le Produit (Clé) à la Quantité (Valeur)
    private Map<Produit, Integer> stockMap = new HashMap<>();

    // Constructeur : on charge les données dès le démarrage
    public StockService() {
        chargerDonneesDepuisDB();
    }

    // Récupérer les données de Postgres et remplir la Map
    private void chargerDonneesDepuisDB() {
        stockMap.clear();
        String sql = "SELECT * FROM produits";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produit p = new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix")
                );
                int qte = rs.getInt("quantite");
                stockMap.put(p, qte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour ajouter un produit (DB + Map)
    public void ajouterProduit(String nom, String desc, double prix, int qte) {
        String sql = "INSERT INTO produits (nom, description, prix, quantite) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, desc);
            pstmt.setDouble(3, prix);
            pstmt.setInt(4, qte);
            pstmt.executeUpdate();

            // Recharger la map pour être synchro
            chargerDonneesDepuisDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter pour l'interface graphique
    public Map<Produit, Integer> getStockMap() {
        return stockMap;
    }
}