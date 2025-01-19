package tp1.interfacedevoire;

import java.util.ArrayList;

public class GestionItems {
    // Liste statique pour stocker les items
    private static ArrayList<Item> listeItems = new ArrayList<>();

    // Méthode pour obtenir la liste des items
    public static ArrayList<Item> getListeItems() {
        return listeItems;
    }

    // Méthode pour ajouter un item à la liste
    public static void ajouterItem(Item item) {
        listeItems.add(item);
    }

    // Méthode pour retirer un item de la liste
    public static void retirerIter(Item item) {
        listeItems.remove(item);
    }
}
