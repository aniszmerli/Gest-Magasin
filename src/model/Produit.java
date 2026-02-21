package model;

import java.util.Objects;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private double prix;
    // Note : La quantité sera gérée dans la Map, mais on peut la garder ici pour faciliter l'affichage DB

    public Produit(int id, String nom, String description, double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public double getPrix() { return prix; }

    // Pour l'affichage dans la console ou les listes
    @Override
    public String toString() {
        return nom + " (" + prix + "€)";
    }

    // OBLIGATOIRE pour l'utilisation des clés dans une HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id == produit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}