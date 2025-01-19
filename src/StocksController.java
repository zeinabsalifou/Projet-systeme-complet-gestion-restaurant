
package tp1.interfacedevoire;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class StocksController implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private Label btnproduit;

    @FXML
    private Label btnachats;

    @FXML
    private Label btncompte;
    @FXML
    private Label btnentree;

    @FXML
    private Label btnfournisseurs;

    @FXML
    private Label btnlivraisons;

    @FXML
    private Label btntransactions;

    @FXML
    private TableView<StockItem> tableView;
    @FXML
    private TableColumn<StockItem, String> produitCol;
    @FXML
    private TableColumn<StockItem, String> uniteStockCol;
    @FXML
    private TableColumn<StockItem, String> stockInitCol;
    @FXML
    private TableColumn<StockItem, String> stockFinaleCol;
    @FXML
    private TableColumn<StockItem, String> statutCol;

    @FXML
    private Button supprimerBtn;
    @FXML
    private Button ajouterProduitBtn;

    @FXML
    private Button boutonPrecedent;

    private final Properties properties = new Properties();
    private final File file = new File("stock.properties");


    public void nivo() {
        // initialisation de TableView et chargement des données initiales
        initTable();
        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Définir l'action lorsque le bouton Ajouter Produit est cliqué
        ajouterProduitBtn.setOnAction(event -> ajouterProduit());

        // Ajout du gestionnaire d'événements au bouton Supprimer Sélectionné
        supprimerBtn.setOnAction(event -> supprimerProduitSelectionne());
    }


    // Méthode pour rechercher produit
    @FXML
    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String enteredText = textField.getText().trim(); // Obtenir le texte du TextField
            if (!enteredText.isEmpty()) { // Vérifier si le texte n'est pas vide
                for (StockItem item : tableView.getItems()) {
                    if (item.getProduit().equalsIgnoreCase(enteredText)) {
                        tableView.getSelectionModel().select(item); // Sélectionner l'élément dans le TableView
                        tableView.scrollTo(item); // Faire défiler pour afficher l'élément sélectionné
                        return;
                    }
                }
            }
        }
    }

    @FXML
    public void searchElement() {
        String enteredText = textField.getText();
        for (StockItem item : tableView.getItems()) {
            if (item.getProduit().equalsIgnoreCase(enteredText)) {
                tableView.getSelectionModel().select(item);
                tableView.scrollTo(item);
                return;
            }
        }
        // Si l'élément n'est pas trouvé, désélectionner tout
        tableView.getSelectionModel().clearSelection();
    }

//Méthode pour supprimer produit de la table
    private void supprimerProduitSelectionne() {
        StockItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tableView.getItems().remove(selectedItem);
            saveStockData(); // Sauvegarde des données après la suppression
        }
    }

    // Méthode pour rendre les cellules editables et modifier les valeurs.
    private void initTable() {
        produitCol.setCellValueFactory(cellData -> cellData.getValue().produitProperty());
        produitCol.setCellFactory(TextFieldTableCell.forTableColumn());
        produitCol.setOnEditCommit(event -> {
            event.getRowValue().setProduit(event.getNewValue());
            saveStockData();
        });

        uniteStockCol.setCellValueFactory(cellData -> cellData.getValue().uniteStockProperty());
        uniteStockCol.setCellFactory(TextFieldTableCell.forTableColumn());
        uniteStockCol.setOnEditCommit(event -> {
            event.getRowValue().setUniteStock(event.getNewValue());
            saveStockData();
        });

        stockInitCol.setCellValueFactory(cellData -> cellData.getValue().stockInitialProperty());
        stockInitCol.setCellFactory(TextFieldTableCell.forTableColumn());
        stockInitCol.setOnEditCommit(event -> {
            event.getRowValue().setStockInitial(event.getNewValue());
            saveStockData();
        });

        stockFinaleCol.setCellValueFactory(cellData -> cellData.getValue().stockFinaleProperty());
        stockFinaleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        stockFinaleCol.setOnEditCommit(event -> {
            event.getRowValue().setStockFinale(event.getNewValue());
            saveStockData();
        });

        statutCol.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
        statutCol.setCellFactory(TextFieldTableCell.forTableColumn());
        statutCol.setOnEditCommit(event -> {
            event.getRowValue().setStatut(event.getNewValue());
            saveStockData();
        });
    }

// Méthode pour pour rendre les labels bleus , lorsqu'on passe le curseur.
    public void onMouseEnter(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: #87CEEB;");

        label.setCursor(Cursor.HAND);
    }

    public void onMouseExit(MouseEvent event) {
        Label label = (Label) event.getSource();
        label.setStyle("-fx-background-color: transparent;");
        label.setCursor(Cursor.DEFAULT);
    }

// méthode pour afficher le compte de stock
    @FXML
    public void onCompteStocksClick() {
        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("compte.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            CompteController compteController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Compte de stocks"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

            // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);

        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    @FXML
    public void onGestionProduitsClick() {
        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("produit.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            ProduitController produitController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Gestion de produits"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

            // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);

        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    //Méthode pour charger les données.
    private void loadStockData() {
        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
                for (String key : properties.stringPropertyNames()) {
                    String[] values = properties.getProperty(key).split(",");
                    if (values.length == 5) {
                        StockItem item = new StockItem(values[0], values[1], values[2], values[3], values[4]);
                        tableView.getItems().add(item);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            tableView.getItems().add(new StockItem("Produit 1", "Unité 1", "10", "5", "En stock"));
            tableView.getItems().add(new StockItem("Produit 2", "Unité 2", "20", "15", "En rupture"));
            tableView.getItems().add(new StockItem("Produit 3", "Unité 3", "15", "8", "En commande"));
            saveStockData();
        }
    }

    private void saveStockData() {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            // Effacer le contenu du fichier
            properties.clear();

            // Réécrire toutes les données restantes dans le fichier
            for (StockItem item : tableView.getItems()) {
                String value = String.join(",", item.getProduit(), item.getUniteStock(), item.getStockInitial(),
                        item.getStockFinale(), item.getStatut());
                properties.setProperty(item.getProduit(), value);
            }
            // Enregistrer les données mises à jour dans le fichier
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour ajouter un produit dans le tableau.
    @FXML
    private void ajouterProduit() {
        tableView.getItems().add(new StockItem(" ", " ", " ", " ", " "));
        saveStockData();
    }
    @FXML
    public void onNiveauStocksClick(MouseEvent mouseEvent) {
        nivo();
    }

    //Méthode pour passer a l'interface de comptes de stocks.
    @FXML
    private void  passerAutreInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("compte.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btncompte.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface de gestion de produits.
    @FXML
    private void passerInterfaceProduit() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("produit.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btnproduit.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface de entrees et sorties.
    @FXML
    private void passerInterfaceEntree() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("entree.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btnentree.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface d'achats.
    @FXML
    private void passerInterfaceAchats() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("achats.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btnachats.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface de transactions.
    @FXML
    private void passerInterfaceTransactions() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btntransactions.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface de livraisons.
    @FXML
    private void passerInterfaceLivraisons() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livraisons.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btnlivraisons.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour passer a l'interface de liste de fournisseurs.
    @FXML
    private void passerInterfaceFournisseurs() {
        try {
            // Charger le fichier FXML de l'autre interface (compte-view.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseurs.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu de l'autre interface
            Scene scene = new Scene(root, 1500, 800);

            // Obtenir la fenêtre principale depuis le bouton (ou tout autre nœud dans la hiérarchie)
            Stage stage = (Stage) btnfournisseurs.getScene().getWindow();

            // Changer la scène de la fenêtre principale pour afficher l'autre interface
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gestion de l'exception IO si le chargement du FXML échoue
        }
    }

    // Méthode pour afficher l'interface d'entrées et sorties.
    public void onEntreesSortiesClick(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("entree.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            EntreeController entreeController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Entrées et sorties"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

           // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    // Méthode pour afficher l'interface d'achats de marchandises.
    public void onAchatsClick(MouseEvent mouseEvent) {

        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("achats.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            AchatsController achatsController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Achats de marchandises"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

            // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    // Méthode pour afficher l'interface de transactions.
    public void onTransactionsClick(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            TransactionsController coController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Transactions d'achats"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

           // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    // Méthode pour afficher l'interface de suivi de livraisons.
    public void onLivraisonsClick(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livraisons.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            LivraisonsController entController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Suivi des livraisons"); // Titre de la fenêtre
            stage.setScene(new Scene(root, 1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

           // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

    // Méthode pour afficher l'interface de liste de fournisseurs.
    public void onFournisseursClick(MouseEvent mouseEvent) {

        try {
            // Charger le fichier FXML pour la vue Compte
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fournisseurs.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur associé à la vue Compte
            FournisseursController fournController = loader.getController();

            // Créer une nouvelle fenêtre pour afficher la vue Compte
            Stage stage = new Stage();
            stage.setTitle("Liste des fournisseurs"); // Titre de la fenêtre
            stage.setScene(new Scene(root,1500, 800)); // Configurer la scène avec la vue chargée
            stage.show(); // Afficher la fenêtre

            // Chargement de l'icône depuis le dossier de ressources "images"
            Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

           // Ajouter l'icône à la barre de titre de la fenêtre
            stage.getIcons().add(flame);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions en cas d'erreur de chargement ou d'affichage
        }
    }

//méthode d'entrée.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nivo();
        loadStockData();
    }

    public void retourInterfa(ActionEvent actionEvent) throws IOException {
        // Charger une nouvelle interface
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close(); // Fermer l'interface actuelle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_Gestion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // la classe de gestion de stocks.
    public static class StockItem {
        private final SimpleStringProperty produit;
        private final SimpleStringProperty uniteStock;
        private final SimpleStringProperty stockInitial;
        private final SimpleStringProperty stockFinale;
        private final SimpleStringProperty statut;

        // Constructeur.
        public StockItem(String produit, String uniteStock, String stockInitial, String stockFinale, String statut) {
            this.produit = new SimpleStringProperty(produit);
            this.uniteStock = new SimpleStringProperty(uniteStock);
            this.stockInitial = new SimpleStringProperty(stockInitial);
            this.stockFinale = new SimpleStringProperty(stockFinale);
            this.statut = new SimpleStringProperty(statut);
        }

        // Getters et Setters.
        public String getStockInitial() {
            return stockInitial.get();
        }

        public SimpleStringProperty stockInitialProperty() {

            return stockInitial;
        }

        public void setStockInitial(String stockInitial) {
            this.stockInitial.set(stockInitial);
        }

        public String getStockFinale() {

            return stockFinale.get();
        }

        public SimpleStringProperty stockFinaleProperty() {

            return stockFinale;
        }

        public void setStockFinale(String stockFinale) {

            this.stockFinale.set(stockFinale);
        }

        public String getProduit() {

            return produit.get();
        }

        public SimpleStringProperty produitProperty() {

            return produit;
        }

        public void setProduit(String produit) {

            this.produit.set(produit);
        }

        public String getUniteStock() {

            return uniteStock.get();
        }

        public SimpleStringProperty uniteStockProperty() {

            return uniteStock;
        }

        public void setUniteStock(String uniteStock) {

            this.uniteStock.set(uniteStock);
        }

        public String getStatut() {

            return statut.get();
        }

        public SimpleStringProperty statutProperty() {

            return statut;
        }

        public void setStatut(String statut) {

            this.statut.set(statut);
        }
    }

}