package tp1.interfacedevoire;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

// Controller de l'interface d'entrée
public class EntreeController implements Initializable {

    @FXML
    private TableView<Item> tableView3;

    @FXML
    private TableColumn<Item, String> productNameCol;

    @FXML
    private TableColumn<Item, Integer> initialStockCol;

    @FXML
    private TableColumn<Item, Integer> entriesCol;

    @FXML
    private TableColumn<Item, Integer> exitsCol;

    @FXML
    private TableColumn<Item, Integer> finalStockCol;

    @FXML
    Button ajouterItem;

    @FXML
    Button supprimerItem;

    @FXML
    Button btnPre;

    private final ObservableList<Item> stockItems = FXCollections.observableArrayList();

    private final File dataFile = new File("stock_data.txt");

    // Méthode d'initialisation
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        setupTable();

        // Ajout d'un gestionnaire d'événements au bouton Ajouter Item
        ajouterItem.setOnAction(event -> ajouterItem());

        // Ajout d'un gestionnaire d'événements au bouton Supprimer Item
        supprimerItem.setOnAction(event -> supprimerItem());

        // Ajout d'un gestionnaire d'événements au bouton Précédent
        btnPre.setOnAction(this::retourInterfacep);
    }

    // Configuration de la table
    private void setupTable() {
        tableView3.setItems(stockItems);
        tableView3.setEditable(true);

        productNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        initialStockCol.setCellValueFactory(cellData -> cellData.getValue().initialStockProperty().asObject());
        entriesCol.setCellValueFactory(cellData -> cellData.getValue().entriesProperty().asObject());
        exitsCol.setCellValueFactory(cellData -> cellData.getValue().exitsProperty().asObject());
        finalStockCol.setCellValueFactory(cellData -> cellData.getValue().finalStockProperty().asObject());

        initialStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        entriesCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        exitsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        setupCellEditHandlers();
    }

    // Configuration des gestionnaires de modification de cellules
    private void setupCellEditHandlers() {
        productNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameCol.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setProductName(event.getNewValue());
            saveData();
        });

        initialStockCol.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setInitialStock(event.getNewValue());
            updateFinalStock(item);
            saveData();
        });

        entriesCol.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setEntries(event.getNewValue());
            updateFinalStock(item);
            saveData();
        });

        exitsCol.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setExits(event.getNewValue());
            updateFinalStock(item);
            saveData();
        });
    }

    // Mise à jour du stock final en fonction des entrées et sorties
    private void updateFinalStock(Item item) {
        item.setFinalStock(item.getInitialStock() + item.getEntries() - item.getExits());
    }

    // Chargement des données à partir du fichier
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Item item = new Item(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                stockItems.add(item);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Sauvegarde des données dans le fichier
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            for (Item item : stockItems) {
                writer.printf("%s,%d,%d,%d,%d%n", item.getProductName(), item.getInitialStock(),
                        item.getEntries(), item.getExits(), item.getFinalStock());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ajout d'un nouvel élément à la table
    @FXML
    private void ajouterItem() {
        Item newItem = new Item(" ", 0, 0, 0, 0);
        stockItems.add(newItem);
        saveData();
    }

    // Suppression de l'élément sélectionné dans la table
    @FXML
    private void supprimerItem() {
        Item selectedItem = tableView3.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            stockItems.remove(selectedItem);
            saveData();
        }
    }

    // Fermeture de la fenêtre actuelle
    public void retourInterfacep(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    // Classe interne représentant un Item
    public class Item {
        private final StringProperty productName;
        private final IntegerProperty initialStock;
        private final IntegerProperty entries;
        private final IntegerProperty exits;
        private final IntegerProperty finalStock;

        // Constructeur
        public Item(String productName, int initialStock, int entries, int exits, int finalStock) {
            this.productName = new SimpleStringProperty(productName);
            this.initialStock = new SimpleIntegerProperty(initialStock);
            this.entries = new SimpleIntegerProperty(entries);
            this.exits = new SimpleIntegerProperty(exits);
            this.finalStock = new SimpleIntegerProperty(finalStock);
        }

        // Getters et Setters pour chaque propriété
        public String getProductName() {
            return productName.get();
        }

        public Integer getInitialStock() {
            return initialStock.get();
        }

        public Integer getEntries() {
            return entries.get();
        }

        public Integer getExits() {
            return exits.get();
        }

        public Integer getFinalStock() {
            return finalStock.get();
        }

        public StringProperty productNameProperty() {
            return productName;
        }

        public IntegerProperty initialStockProperty() {
            return initialStock;
        }

        public IntegerProperty entriesProperty() {
            return entries;
        }

        public IntegerProperty exitsProperty() {
            return exits;
        }

        public IntegerProperty finalStockProperty() {
            return finalStock;
        }

        public void setProductName(String productName) {
            this.productName.set(productName);
        }

        public void setInitialStock(int initialStock) {
            this.initialStock.set(initialStock);
        }

        public void setEntries(int entries) {
            this.entries.set(entries);
        }

        public void setExits(int exits) {
            this.exits.set(exits);
        }

        public void setFinalStock(int finalStock) {
            this.finalStock.set(finalStock);
        }
    }
}
