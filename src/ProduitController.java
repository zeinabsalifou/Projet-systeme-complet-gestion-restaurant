package tp1.interfacedevoire;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    private final Properties properties = new Properties();
    private final File file = new File("produit.properties");

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> categoryCol;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> unitCol;

    @FXML
    private TableColumn<Product, Integer> quantityCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Double> amountCol;

    @FXML
    Button ajouterProduit;

    @FXML
    Button supprimerProduit;

    @FXML
    Button btnPrece;

    ObservableList<Product> productList = FXCollections.observableArrayList(
            new Product("Catégorie A", "Produit 1", "Unité A", 50, 10.99),
            new Product("Catégorie B", "Produit 2", "Unité B", 30, 15.75),
            new Product("Catégorie C", "Produit 3", "Unité C", 20, 5.99));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDataFromPropertiesFile(); // Charger les données à partir du fichier de propriétés

        productTable.setItems(productList); // Définissez la liste dans le TableView

        // Configurez les cellules éditables et les gestionnaires d'événements.
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        unitCol.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        productTable.setEditable(true);
        categoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        unitCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        categoryCol.setOnEditCommit(this::updateCategory);
        nameCol.setOnEditCommit(this::updateName);
        unitCol.setOnEditCommit(this::updateUnit);
        quantityCol.setOnEditCommit(this::updateQuantity);
        priceCol.setOnEditCommit(this::updatePrice);

        // Définir l'action lorsque le bouton Ajouter Produit est cliqué
        ajouterProduit.setOnAction(event -> ajouterProduit());

        // Ajout du gestionnaire d'événements au bouton Supprimer Sélectionné
        supprimerProduit.setOnAction(event -> supprimerProduit());

        productTable.refresh(); // Rafraîchir le TableView après l'ajout

        // Lier de l'événement du bouton "Précédent" avec la méthode de fermeture de l'interface
        btnPrece.setOnAction(this::retourInterfacepri);

    }

    //méthodes pour modifier les champs.
    private void updateCategory(TableColumn.CellEditEvent<Product, String> event) {
        Product product = event.getRowValue();
        product.setCategory(event.getNewValue());
        saveDataToPropertiesFile();
    }

    private void updateName(TableColumn.CellEditEvent<Product, String> event) {
        Product product = event.getRowValue();
        product.setName(event.getNewValue());
        saveDataToPropertiesFile();
    }

    private void updateUnit(TableColumn.CellEditEvent<Product, String> event) {
        Product product = event.getRowValue();
        product.setUnit(event.getNewValue());
        saveDataToPropertiesFile();
    }

    private void updateQuantity(TableColumn.CellEditEvent<Product, Integer> event) {
        Product product = event.getRowValue();
        product.setQuantity(event.getNewValue());
        saveDataToPropertiesFile();
    }

    private void updatePrice(TableColumn.CellEditEvent<Product, Double> event) {
        Product product = event.getRowValue();
        product.setPrice(event.getNewValue());
        saveDataToPropertiesFile();
    }

    //Méthode pour ajouter un nouveau produit.
    @FXML
    private void ajouterProduit() {
        Product newProduct = new Product("Nouvelle Catégorie", "Nouveau Produit", "Nouvelle Unité", 0, 0.0);
        productList.add(newProduct);
        saveDataToPropertiesFile();
        productTable.setItems(productList);
        productTable.refresh();
    }

    // Méthode pour supprimer produit.
    @FXML
    private void supprimerProduit() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            saveDataToPropertiesFile();
            productTable.refresh();
        } else {
            // Afficher un message d'erreur ou une notification indiquant qu'aucun produit n'est sélectionné

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun Produit Sélectionné");
            alert.setHeaderText("Aucun produit n'a été sélectionné.");
            alert.setContentText("Veuillez sélectionner un produit à supprimer.");
            alert.showAndWait();
        }
    }

    // Methodes pour sauvegarder les données.
    private void saveDataToPropertiesFile() {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                String keyPrefix = "Produit" + (i + 1) + ".";
                properties.setProperty(keyPrefix + "category", product.getCategory());
                properties.setProperty(keyPrefix + "unit", product.getUnit());
                properties.setProperty(keyPrefix + "quantity", String.valueOf(product.getQuantity()));
                properties.setProperty(keyPrefix + "price", String.valueOf(product.getPrice()));
            }

            // Supprimer les données du produit du fichier de propriétés si le produit a été supprimé de productList
            int numProducts = productList.size();
            while (properties.containsKey("Produit" + (numProducts + 1) + ".category")) {
                properties.remove("Produit" + (numProducts + 1) + ".category");
                properties.remove("Produit" + (numProducts + 1) + ".unit");
                properties.remove("Produit" + (numProducts + 1) + ".quantity");
                properties.remove("Produit" + (numProducts + 1) + ".price");
                numProducts++;
            }
            properties.store(outputStream, "Stock data updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les données.
    private void loadDataFromPropertiesFile() {
        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);

                productList.clear(); // Effacer la liste actuelle avant de charger à nouveau les données
                int index = 1;
                while (properties.containsKey("Produit" + index + ".category")) {
                    Product product = new Product(
                            properties.getProperty("Produit" + index + ".category"),
                            "Produit " + index,
                            properties.getProperty("Produit" + index + ".unit"),
                            Integer.parseInt(properties.getProperty("Produit" + index + ".quantity")),
                            Double.parseDouble(properties.getProperty("Produit" + index + ".price"))
                    );
                    productList.add(product);
                    index++;
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // méthode pour fermer la fenêtre actuelle.
    public void retourInterfacepri(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // classe pour la creation de produits.
    public class Product {

        private StringProperty category;
        private StringProperty name;
        private StringProperty unit;
        private IntegerProperty quantity;
        private DoubleProperty price;
        private DoubleProperty amount;

        //Constructeur.
        public Product(String category, String name, String unit, int quantity, double price) {
            this.category = new SimpleStringProperty(category);
            this.name = new SimpleStringProperty(name);
            this.unit = new SimpleStringProperty(unit);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
            this.amount = new SimpleDoubleProperty(quantity * price); // Calcul du montant initial
        }

        // Getters et setters
        public String getCategory() {
            return category.get();
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public StringProperty categoryProperty() {
            return category;
        }

        // Getters pour les autres propriétés
        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getUnit() {
            return unit.get();
        }

        public StringProperty unitProperty() {
            return unit;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public double getPrice() {
            return price.get();
        }

        public DoubleProperty priceProperty() {
            return price;
        }

        public double getAmount() {
            return amount.get();
        }

        public DoubleProperty amountProperty() {
            return amount;
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
            updateAmount(); // Met à jour le montant lorsque la quantité change
        }

        public void setPrice(double price) {
            this.price.set(price);
            updateAmount(); // Met à jour le montant lorsque le prix unitaire change
        }

        private void updateAmount() {
            amount.set(quantity.get() * price.get());
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public void setUnit(String unit) {
            this.unit.set(unit);
        }
    }
}
