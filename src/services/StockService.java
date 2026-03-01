package services;

import model.Produit;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StockService {

    private Map<Produit, Integer> stockMap = new HashMap<>();

    public StockService() {
        chargerDonneesDepuisDB();
    }

    private void chargerDonneesDepuisDB() {
        stockMap.clear();
        String sql = "SELECT id, nom, description, prix, quantite FROM produits";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produit p = new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix")
                );
                stockMap.put(p, rs.getInt("quantite"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterProduit(String nom, String description, double prix, int quantite) {
        String sql = "INSERT INTO produits (nom, description, prix, quantite) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, description);
            pstmt.setDouble(3, prix);
            pstmt.setInt(4, quantite);
            pstmt.executeUpdate();

            chargerDonneesDepuisDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterQuantite(int produitId, int nouvelleQuantite) {
        String sql = "UPDATE produits SET quantite = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nouvelleQuantite);
            pstmt.setInt(2, produitId);
            pstmt.executeUpdate();

            chargerDonneesDepuisDB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Produit, Integer> getStockMap() {
        return Collections.unmodifiableMap(stockMap);
    }
}