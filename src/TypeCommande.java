package tp1.interfacedevoire;

// Déclaration de l'énumération TypeCommande
public enum TypeCommande {
    TABLE ("Table"),           // Type de commande pour une table
    LIVRAISON ("Livraison"),   // Type de commande pour une livraison
    A_EMPORTER ("À emporter"); // Type de commande pour emporter

    private String typeCommande; // Attribut pour stocker le type de commande

    // Constructeur de l'énumération TypeCommande
    TypeCommande(String typeCommande) {
        this.typeCommande = typeCommande; // Initialisation de l'attribut avec la valeur passée en paramètre
    }

    // Méthode pour obtenir le type de commande
    public String getTypeCommandeEnum() {
        return typeCommande; // Retourne le type de commande
    }
}
