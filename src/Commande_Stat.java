package tp1.interfacedevoire;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Cette classe représente les statistiques associées à une commande
public class Commande_Stat {

    // Propriétés pour le numéro de commande, le temps de préparation, le temps de livraison et la confirmation de livraison
    private SimpleStringProperty numeroCommande;
    private SimpleIntegerProperty tempsPreparation;
    private SimpleIntegerProperty tempsLivraison;
    private SimpleBooleanProperty confirmationLivraison;

    // Constructeur prenant les valeurs initiales pour chaque propriété
    public Commande_Stat(String numeroCommande, int tempsPreparation, int tempsLivraison, boolean confirmationLivraison) {
        this.numeroCommande = new SimpleStringProperty(numeroCommande);
        this.tempsPreparation = new SimpleIntegerProperty(tempsPreparation);
        this.tempsLivraison = new SimpleIntegerProperty(tempsLivraison);
        this.confirmationLivraison = new SimpleBooleanProperty(confirmationLivraison);
    }

    // Getters pour chaque propriété
    public String getNumeroCommande() {
        return numeroCommande.get();
    }

    public int getTempsPreparation() {
        return tempsPreparation.get();
    }

    public int getTempsLivraison() {
        return tempsLivraison.get();
    }

    public boolean isConfirmationLivraison() {
        return confirmationLivraison.get();
    }

    // Setters pour la confirmation de livraison
    public void setConfirmationLivraison(boolean value) {
        confirmationLivraison.set(value);
    }
}
