package tp1.interfacedevoire;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

// Contrôleur pour l'interface de gestion des comptes
public class CompteController implements Initializable {

    // Attributs représentant les éléments graphiques de l'interface
    @FXML
    private TableView<StockAccount> tableView2;
    @FXML
    private TableColumn<StockAccount, String> nomCol;
    @FXML
    private TableColumn<StockAccount, LocalDate> dateCol;
    @FXML
    private TableColumn<StockAccount, String> employeCol;
    @FXML
    private TableColumn<StockAccount, String> valeurStockCol;
    @FXML
    private TableColumn<StockAccount, String> varianceTotaleCol;
    @FXML
    private TableColumn<StockAccount, String> valeurStockFin;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button btnPrecedent;

    // Chemin du fichier de sauvegarde des données des comptes
    private static final String FILE_PATH = "comptes.txt";

    // Liste observable pour stocker les comptes
    private ObservableList<StockAccount> stockAccounts = FXCollections.observableArrayList();

    // Méthode d'initialisation de l'interface
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger les données depuis le fichier
        loadDataFromFiles();
        // Configurer les colonnes du TableView
        setupColumns();
        // Action du bouton d'ajout
        ajouterButton.setOnAction(this::handleAjouterButton);
        // Action du bouton de retour
        btnPrecedent.setOnAction(this::retourInterfacePrincipale);
    }

    // Configuration des colonnes du TableView
    private void setupColumns() {
        // Permettre l'édition des cellules dans le TableView
        tableView2.setEditable(true);

        // Configuration de chaque colonne avec ses propriétés
        // Nom
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomCol.setOnEditCommit(event -> {
            StockAccount selectedItem = event.getRowValue();
            selectedItem.setNom(event.getNewValue());
            saveData();
        });

        // Employé
        employeCol.setCellValueFactory(new PropertyValueFactory<>("employe"));
        employeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        employeCol.setOnEditCommit(event -> {
            StockAccount selectedItem = event.getRowValue();
            selectedItem.setEmploye(event.getNewValue());
            saveData();
        });

        // Valeur du stock
        valeurStockCol.setCellValueFactory(new PropertyValueFactory<>("valeurStock"));
        valeurStockCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valeurStockCol.setOnEditCommit(event -> {
            StockAccount selectedItem = event.getRowValue();
            selectedItem.setValeurStock(event.getNewValue());
            selectedItem.updateVarianceTotal();
            saveData();
            tableView2.refresh();
        });

        // Valeur finale du stock
        valeurStockFin.setCellValueFactory(new PropertyValueFactory<>("valeurFinale"));
        valeurStockFin.setCellFactory(TextFieldTableCell.forTableColumn());
        valeurStockFin.setOnEditCommit(event -> {
            StockAccount selectedItem = event.getRowValue();
            selectedItem.setValeurFinale(event.getNewValue());
            selectedItem.updateVarianceTotal();
            saveData();
            tableView2.refresh();
        });

        // Date
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Colonne pour la variance totale
        varianceTotaleCol.setCellValueFactory(new PropertyValueFactory<>("varianceTotale"));

        // Configuration du TableView avec les données stockAccounts
        tableView2.setItems(stockAccounts);
    }

    // Méthode pour gérer l'ajout d'un compte
    @FXML
    private void handleAjouterButton(ActionEvent event) {
        StockAccount newAccount = new StockAccount("", LocalDate.now(), "", "", "", "");
        stockAccounts.add(newAccount);
        tableView2.refresh(); // Rafraîchir le tableau pour afficher la nouvelle ligne
    }

    // Méthode pour initialiser les données de la table (utilisé pour tester)
    private void initializeTable() {
        // Ajout de comptes de démonstration
        // (Nom, Date, Employé, Valeur du stock, Valeur finale, Variance totale)
        stockAccounts.clear();
        stockAccounts.add(new StockAccount("Compte de bar", LocalDate.now(), "John Doe", "5000$", "200$", "valeur finale 1"));
        stockAccounts.add(new StockAccount("Compte de cuisine", LocalDate.now(), "Jane Smith", "8000$", "300$", "valeur finale 2"));
        stockAccounts.add(new StockAccount("Compte de café", LocalDate.now(), "Alice Johnson", "3000$", "100$", "valeur finale 3"));
        stockAccounts.add(new StockAccount("Compte de bar", LocalDate.now(), "zeinab", "8000$", "400$", "valeur finale 4"));
        saveData(); // Sauvegarde des données initiales dans le fichier
    }

    // Méthode pour charger les données depuis le fichier
    private void loadDataFromFiles() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 6) {
                    try {
                        // Création d'un compte à partir des données et ajout à la liste
                        StockAccount account = new StockAccount(data[0], LocalDate.parse(data[1]),
                                data[2], data[3], data[4], data[5]);
                        stockAccounts.add(account);
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement des données : " + e.getMessage());
                    }
                } else {
                    System.err.println("Ligne invalide dans le fichier : " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des données depuis le fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour sauvegarder les données dans le fichier
    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (StockAccount account : stockAccounts) {
                // Écriture des données de chaque compte dans une ligne du fichier
                writer.write(account.getNom() + ";" + account.getDate() + ";" + account.getEmploye()
                        + ";" + account.getValeurStock() + ";" + account.getValeurFinale() + ";" + account.getVarianceTotale());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour fermer la fenêtre actuelle
    public void retourInterfacePrincipale(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Classe interne pour représenter un compte
    public static class StockAccount {
        // Propriétés des colonnes de la table
        private final SimpleStringProperty nom;
        private final SimpleObjectProperty<LocalDate> date;
        private final SimpleStringProperty employe;
        private final SimpleStringProperty valeurStock;
        private final SimpleStringProperty valeurFinale;
        private final SimpleStringProperty varianceTotale;

        // Constructeur
        public StockAccount(String nom, LocalDate date, String employe, String valeurStock, String valeurFinale, String varianceTotale) {
            this.nom = new SimpleStringProperty(nom);
            this.date = new SimpleObjectProperty<>(date);
            this.employe = new SimpleStringProperty(employe);
            this.valeurStock = new SimpleStringProperty(valeurStock);
            this.valeurFinale = new SimpleStringProperty(valeurFinale);
            this.varianceTotale = new SimpleStringProperty(varianceTotale);

            // Ajout des écouteurs pour mettre à jour la variance totale lors des modifications
            this.valeurStock.addListener((observable, oldValue, newValue) -> updateVarianceTotal());
            this.valeurFinale.addListener((observable, oldValue, newValue) -> updateVarianceTotal());
        }

        // Méthode pour mettre à jour la variance totale
        private void updateVarianceTotal() {
            BigDecimal valeurStockValue = parseMoneyValue(valeurStock.get());
            BigDecimal valeurFinaleValue = parseMoneyValue(valeurFinale.get());
            BigDecimal variance = valeurFinaleValue.subtract(valeurStockValue);
            varianceTotale.set(String.format("%.2f$", variance));
        }

        // Méthode pour convertir une valeur monétaire en BigDecimal
        private BigDecimal parseMoneyValue(String moneyValue) {
            return new BigDecimal(moneyValue.replace("$", "").trim());
        }

        // Getters et setters des propriétés
        public String getValeurFinale() {
            return valeurFinale.get();
        }

        public SimpleStringProperty valeurFinaleProperty() {
            return valeurFinale;
        }

        public void setValeurFinale(String valeurFinale) {
            this.valeurFinale.set(valeurFinale);
        }

        public LocalDate getDate() {
            return date.get();
        }

        public SimpleObjectProperty<LocalDate> dateProperty() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date.set(date);
        }

        public String getNom() {
            return nom.get();
        }

        public SimpleStringProperty nomProperty() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom.set(nom);
        }

        public String getEmploye() {
            return employe.get();
        }

        public SimpleStringProperty employeProperty() {
            return employe;
        }

        public void setEmploye(String employe) {
            this.employe.set(employe);
        }

        public String getValeurStock() {
            return valeurStock.get();
        }

        public SimpleStringProperty valeurStockProperty() {
            return valeurStock;
        }

        public void setValeurStock(String valeurStock) {
            this.valeurStock.set(valeurStock);
        }

        public String getVarianceTotale() {
            return varianceTotale.get();
        }

        public SimpleStringProperty varianceTotaleProperty() {
            return varianceTotale;
        }

        public void setVarianceTotale(String varianceTotale) {
            this.varianceTotale.set(varianceTotale);
        }
    }
}
