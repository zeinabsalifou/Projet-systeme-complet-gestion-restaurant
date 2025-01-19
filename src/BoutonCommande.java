package tp1.interfacedevoire;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

// Cette classe représente un bouton personnalisé pour afficher des informations sur une commande
public class BoutonCommande extends Button {

    // Déclaration des composants GUI
    Label label1; // Premier label pour afficher le type de commande et le numéro de commande
    Label label2; // Deuxième label pour afficher l'état de la commande
    VBox vbox; // Boîte verticale pour contenir les labels
    Commande commande; // La commande associée à ce bouton

    // Constructeur prenant une commande en paramètre
    public BoutonCommande(Commande commande) {
        this.commande = commande;

        // Construction du texte à afficher dans les labels
        String texte1 = commande.getTypeCommande().getTypeCommandeEnum() + "  " + commande.getNO_COMMANDE();
        String texte2 =  commande.getEtatCommande().getEtatCommandeEnum();

        // Initialisation des labels et de la boîte verticale
        label1 = new Label(texte1);
        label2 = new Label(texte2);
        vbox = new VBox();

        // Ajout de style à la boîte verticale
        vbox.getStyleClass().add("vboxBoutonCommande");

        // Ajout des labels à la boîte verticale
        vbox.getChildren().add(label1);
        vbox.getChildren().add(label2);

        // Définition de la boîte verticale comme élément graphique du bouton
        this.setGraphic(vbox);

        // Ajout de style au bouton
        this.getStyleClass().add("boutonCommande");

        // Désactivation du bouton par défaut
        this.setDisable(true);

        // Activation de l'analyse des caractères mnémoniques
        this.setMnemonicParsing(true);
    }

    // Méthode pour définir le texte du deuxième label
    public void setTextLabel2(String texte) {
        label2.setText(texte);
    }

    // Méthode pour définir la couleur du texte du deuxième label
    public void setCouleurLabel2(Color color) {
        label2.setTextFill(color);
    }

    // Méthode pour obtenir l'identifiant de la commande associée
    public String getIdentifiant() {
        return commande.getNO_COMMANDE();
    }

    // Méthode pour obtenir la commande associée
    public Commande getCommande() {
        return commande;
    }

    // Méthode pour obtenir le texte du premier label
    public String getLabel1() {
        return label1.getText();
    }
}
