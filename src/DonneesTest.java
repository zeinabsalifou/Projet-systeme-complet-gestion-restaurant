package tp1.interfacedevoire;

// Cette classe contient des méthodes pour créer des objets Item et Commande à des fins de test
public class DonneesTest {

    // Méthode pour créer des objets Item
    public static void creerItems() {
        // Création des objets Item avec leurs noms, prix et catégories
        Item salade = new Item("Salade du chef", 5.50, CategorieItem.ENTREE);
        Item soupe = new Item("Soupe du jour", 5.50, CategorieItem.ENTREE);
        Item pizza = new Item("Pizza de luxe", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item saumon = new Item("Saumon grillé", 24.00, CategorieItem.PLAT_PRINCIPAL);
        Item escargot = new Item("Escargots à l'ail", 9.00, CategorieItem.ENTREE);
        Item pates = new Item("Pâtes Alfredo", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item tiramisu = new Item("Tiramisu", 5.50, CategorieItem.DESSERT);
        Item tarte = new Item("Tarte aux pacanes", 5.50, CategorieItem.DESSERT);
        Item cafe = new Item("Café", 3.50, CategorieItem.BOISSON);
        Item liqueur = new Item("Boisson gazeuse", 3.50, CategorieItem.BOISSON);
        Item vin = new Item("Vin rouge", 10.00, CategorieItem.BOISSON);
    }

    // Méthode pour créer une commande avec des items pour la livraison
    public static Commande creerCommande1() {
        // Création des objets Item pour cette commande
        Item pizza = new Item("Pizza de luxe", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item tarte = new Item("Tarte aux pacanes", 5.50, CategorieItem.DESSERT);
        Item liqueur = new Item("Boisson gazeuse", 3.50, CategorieItem.BOISSON);

        // Création de la commande de type livraison et ajout des items
        Commande commande1 = new Commande(TypeCommande.LIVRAISON);
        commande1.ajouterItem(pizza);
        commande1.ajouterItem(liqueur);
        commande1.ajouterItem(tarte);
        commande1.setEtatCommande(EtatCommande.PRETE_A_LIVRER);
        return commande1;
    }

    // Méthode pour créer une commande avec des items pour le service à table
    public static Commande creerCommande2() {
        // Création des objets Item pour cette commande
        Item salade = new Item("Salade du chef", 5.50, CategorieItem.ENTREE);
        Item saumon = new Item("Saumon grillé", 24.00, CategorieItem.PLAT_PRINCIPAL);
        Item escargot = new Item("Escargots à l'ail", 9.00, CategorieItem.ENTREE);
        Item pates = new Item("Pâtes Alfredo", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item tiramisu = new Item("Tiramisu", 5.50, CategorieItem.DESSERT);
        Item tarte = new Item("Tarte aux pacanes", 5.50, CategorieItem.DESSERT);
        Item cafe = new Item("Café", 3.50, CategorieItem.BOISSON);
        Item vin = new Item("Vin rouge", 10.00, CategorieItem.BOISSON);

        // Création de la commande de type service à table et ajout des items
        Commande commande2 = new Commande(TypeCommande.TABLE);
        commande2.ajouterItem(saumon);
        commande2.ajouterItem(escargot);
        commande2.ajouterItem(vin);
        commande2.ajouterItem(tiramisu);
        commande2.ajouterItem(salade);
        commande2.ajouterItem(vin);
        commande2.ajouterItem(pates);
        commande2.ajouterItem(tarte);
        commande2.ajouterItem(cafe);
        commande2.setEtatCommande(EtatCommande.SERVICE_COMPLETE);
        return commande2;

    }

    // Méthode pour créer une commande avec des items à emporter
    public static Commande creerCommande3() {
        // Création des objets Item pour cette commande
        Item soupe = new Item("Soupe du jour", 5.50, CategorieItem.ENTREE);
        Item pizza = new Item("Pizza de luxe", 17.00, CategorieItem.PLAT_PRINCIPAL);

        // Création de la commande de type à emporter et ajout des items
        Commande commande3 = new Commande(TypeCommande.A_EMPORTER);
        commande3.ajouterItem(pizza);
        commande3.ajouterItem(soupe);
        return commande3;
    }

    // Méthode pour créer une commande avec des items pour le service à table
    public static Commande creerCommande4() {
        // Création des objets Item pour cette commande
        Item salade = new Item("Salade du chef", 5.50, CategorieItem.ENTREE);
        Item soupe = new Item("Soupe du jour", 5.50, CategorieItem.ENTREE);
        Item pizza = new Item("Pizza de luxe", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item saumon = new Item("Saumon grillé", 24.00, CategorieItem.PLAT_PRINCIPAL);
        Item escargot = new Item("Escargots à l'ail", 9.00, CategorieItem.ENTREE);
        Item pates = new Item("Pâtes Alfredo", 17.00, CategorieItem.PLAT_PRINCIPAL);
        Item tiramisu = new Item("Tiramisu", 5.50, CategorieItem.DESSERT);
        Item tarte = new Item("Tarte aux pacanes", 5.50, CategorieItem.DESSERT);
        Item cafe = new Item("Café", 3.50, CategorieItem.BOISSON);
        Item liqueur = new Item("Boisson gazeuse", 3.50, CategorieItem.BOISSON);
        Item vin = new Item("Vin rouge", 10.00, CategorieItem.BOISSON);

        // Création de la commande de type service à table et ajout des items
        Commande commande4 = new Commande(TypeCommande.TABLE);
        commande4.ajouterItem(salade);
        commande4.ajouterItem(soupe);
        commande4.ajouterItem(pizza);
        commande4.ajouterItem(saumon);
        commande4.ajouterItem(escargot);
        commande4.ajouterItem(pates);
        commande4.ajouterItem(tiramisu);
        commande4.ajouterItem(tarte);
        commande4.ajouterItem(tiramisu);
        commande4.ajouterItem(liqueur);
        commande4.ajouterItem(cafe);
        commande4.ajouterItem(vin);
        commande4.setEtatCommande(EtatCommande.SERVICE_COMPLETE);
        return commande4;
    }
}
