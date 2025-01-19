package tp1.interfacedevoire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LivraisonsController {

    @FXML
    private TableView<Livraison> livraisonsTable;

    @FXML
    private TableColumn<Livraison, String> fournisseurs;

    @FXML
    private TableColumn<Livraison, String> dateLivraisonCol;

    @FXML
    private TableColumn<Livraison, String> lieuLivraisonCol;

    @FXML
    private TableColumn<Livraison, String> typeProduit;

    @FXML
    private TableColumn<Livraison, String> statutColonne;

    @FXML
    private Button btnPreced;

    // Initialise les colonnes avec les propriétés des objets Livraison
    public void initialize() {

        fournisseurs.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        typeProduit.setCellValueFactory(new PropertyValueFactory<>("produitLivre"));
        dateLivraisonCol.setCellValueFactory(new PropertyValueFactory<>("dateLivraison"));
        lieuLivraisonCol.setCellValueFactory(new PropertyValueFactory<>("lieuLivraison"));
        statutColonne.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Charger les données dans la table
        chargerDonnees();

        btnPreced.setOnAction(this::retourInterfaceL);
    }

    // Méthode pour charger les données dans la table
    private void chargerDonnees() {

        livraisonsTable.getItems().addAll(
                new Livraison("Maxplus","Viande", "11/04/2024", "Restaurant la Marmite", "En cours"),
                new Livraison("Métro", "Fromage","09/03/2024", "Restaurant la Marmite", "En cours"),
                new Livraison("Narturfresh","Kiwi", "29/03/2024", "Restaurant la Marmite", "Livré"),
                new Livraison("Castel", "Biere","25/03/2024", "Restaurant la Marmite", "En cours"),
                new Livraison("Biomarin","Crevettes", "24/03/2024", "Restaurant la Marmite", "Livré"),
                new Livraison("Vitnet", "Produits d'entretien","22/03/2024", "Restaurant la Marmite", "Livré")
        );
    }

    // Fermer la fenêtre actuelle
    public void retourInterfaceL(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Classe Livraison
    public static class Livraison {
        private String fournisseur;
        private String dateLivraison;
        private String lieuLivraison;
        private String statut;
        private String produitLivre;

        public Livraison(String fournisseur, String produitLivre,String dateLivraison, String lieuLivraison, String statut) {
            this.fournisseur = fournisseur;
            this.dateLivraison = dateLivraison;
            this.lieuLivraison = lieuLivraison;
            this.statut = statut;
            this.produitLivre = produitLivre;
        }

        // Getters
        public String getFournisseur() {
            return fournisseur;
        }

        public String getDateLivraison() {
            return dateLivraison;
        }

        public String getLieuLivraison() {
            return lieuLivraison;
        }

        public String getStatut() {
            return statut;
        }

        public String getProduitLivre() {
            return produitLivre;
        }
    }
}
