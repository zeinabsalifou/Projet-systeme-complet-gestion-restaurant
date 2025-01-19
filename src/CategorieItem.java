package tp1.interfacedevoire;

// Définition de l'énumération CategorieItem
public enum CategorieItem {

    // Déclaration des différentes catégories avec leurs valeurs associées
    ENTREE(0),           // Catégorie pour les entrées
    PLAT_PRINCIPAL(1),  // Catégorie pour les plats principaux
    DESSERT(2),          // Catégorie pour les desserts
    BOISSON(3);          // Catégorie pour les boissons

    // Champ privé pour stocker la valeur de la catégorie
    private int categorieItem;

    // Constructeur privé pour initialiser la valeur de la catégorie
    CategorieItem(int categorieItem) {
        this.categorieItem = categorieItem;
    }

    // Méthode publique pour obtenir la valeur de la catégorie
    public int getCategorieItem() {
        return categorieItem;
    }
}
