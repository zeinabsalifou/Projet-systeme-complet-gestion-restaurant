package tp1.interfacedevoire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public class TransactionsController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    @FXML
    private TableColumn<Transaction, String> supplierColumn;

    @FXML
    private TableColumn<Transaction, String> productColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private Button btnP;

    // Liste observable pour stocker les transactions
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liaison des propriétés des colonnes aux propriétés des transactions
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        productColumn.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        // Définition des éléments de la table à partir de la liste observable
        transactionTable.setItems(transactions);

        // Chargement des transactions
        addTransactions();

        // Définition de l'action à effectuer lorsque le bouton est cliqué
        btnP.setOnAction(this::retourInterfaceT);
    }

    // Méthode pour ajouter des transactions à la liste observable
    public void addTransactions() {
        transactions.addAll(
                new Transaction("2024-04-05", "Métro", "Lait", 150.0),
                new Transaction("2024-04-01", "Naturfresh", "Fraise", 80.0),
                new Transaction("2024-03-29", "Castel", "Biere", 200.0),
                new Transaction("2024-03-25", "Maxplus", "Viande", 120.0),
                new Transaction("2024-03-20", "Makita", "Épices", 50.0),
                new Transaction("2024-03-19", "Vitnet", "Produits d'entretien", 30.0),
                new Transaction("2024-03-16", "Métro", "Viande", 150.0),
                new Transaction("2024-03-09", "Naturfresh", "Mangue", 80.0),
                new Transaction("2024-03-03", "Biomarin", "Poisson", 200.0)
        );
    }

    // Classe interne pour représenter une transaction
    public class Transaction {
        private final SimpleStringProperty date;
        private final SimpleStringProperty supplier;
        private final SimpleStringProperty product;
        private final SimpleDoubleProperty amount;

        // Constructeur de la transaction
        public Transaction(String date, String supplier, String product, double amount) {
            this.date = new SimpleStringProperty(date);
            this.supplier = new SimpleStringProperty(supplier);
            this.product = new SimpleStringProperty(product);
            this.amount = new SimpleDoubleProperty(amount);
        }

        // Méthodes d'accès aux propriétés
        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public String getSupplier() {
            return supplier.get();
        }

        public SimpleStringProperty supplierProperty() {
            return supplier;
        }

        public String getProduct() {
            return product.get();
        }

        public SimpleStringProperty productProperty() {
            return product;
        }

        public double getAmount() {
            return amount.get();
        }

        public SimpleDoubleProperty amountProperty() {
            return amount;
        }
    }

    // Méthode pour fermer la fenêtre actuelle
    public void retourInterfaceT(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
