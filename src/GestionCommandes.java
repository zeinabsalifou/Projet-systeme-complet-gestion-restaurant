package tp1.interfacedevoire;

import java.util.ArrayList;

public class GestionCommandes {

    // Liste statique pour stocker les commandes
    private static ArrayList<Commande> listeCommandes = new ArrayList<>();

    // Constructeur de la classe
    public GestionCommandes() {
        // Création de quelques commandes de test
        Commande commande1 = DonneesTest.creerCommande1();
        Commande commande2 = DonneesTest.creerCommande2();
        Commande commande3 = DonneesTest.creerCommande3();

        // Ajout des commandes à la liste
        listeCommandes.add(commande1);
        listeCommandes.add(commande2);
        listeCommandes.add(commande3);
    }

    // Méthode pour obtenir la liste des commandes
    public static ArrayList<Commande> getListeCommandes() {
        return listeCommandes;
    }

    // Méthode pour ajouter une commande à la liste
    public static void ajouterCommande(Commande commande) {
        listeCommandes.add(commande);
    }

    // Méthode pour supprimer une commande de la liste
    public static void supprimerCommande(Commande commande) {
        listeCommandes.remove(commande);
    }

}
