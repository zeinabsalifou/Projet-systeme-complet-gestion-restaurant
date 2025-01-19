package tp1.interfacedevoire;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item implements Comparable<Item> {

    // Attributs
    private StringProperty nom;
    private DoubleProperty prix;
    private CategorieItem categorieItem;

    // Constructeur
    public Item(StringProperty nom, DoubleProperty prix, CategorieItem categorie) {
        this.nom = nom;
        this.prix = prix;
        this.categorieItem = categorie;
        GestionItems.ajouterItem(this); // Ajouter l'item à la liste des items
    }

    // Constructeur alternatif
    public Item(String nom, double prix, CategorieItem categorieItem) {
        this.nom = new SimpleStringProperty(nom);
        this.prix = new SimpleDoubleProperty(prix);
        this.categorieItem = categorieItem;
    }

    // Méthode getter pour le nom de l'item
    public StringProperty getNom() {
        return nom;
    }

    // Méthode getter pour le prix de l'item
    public DoubleProperty getPrix() {
        return prix;
    }

    // Méthode getter pour la catégorie de l'item
    public CategorieItem getCategorie() {
        return categorieItem;
    }

    // Méthode pour afficher l'item sous forme de chaîne de caractères
    public String toString() {
        return nom.toString();
    }

    // Méthode pour comparer les items en fonction de leur catégorie
    @Override
    public int compareTo(Item item) {
        return categorieItem.compareTo(item.getCategorie());
    }
}
