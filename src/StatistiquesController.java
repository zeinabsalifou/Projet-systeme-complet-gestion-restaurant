package tp1.interfacedevoire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class StatistiquesController {

    @FXML
    private TableView<Commande_Stat> orderTrackingTable;
    @FXML
    private TableColumn<Commande, String> commandColumn;
    @FXML
    private TableColumn<Commande, Integer> prepTimeColumn;
    @FXML
    private TableColumn<Commande, Integer> deliveryTimeColumn;
    @FXML
    private TableColumn<Commande, Boolean> confirmationColumn;
    @FXML
    private TableView<PlatPopulaire> popularDishesTable;
    @FXML
    private TableColumn<PlatPopulaire, String> dishNameColumn;
    @FXML
    private TableColumn<PlatPopulaire, Integer> orderCountColumn;
    @FXML
    private TableColumn<PlatPopulaire, Double> ratingColumn;
    @FXML
    private PieChart pieChart;

    @FXML
    private void initialize() {
        // Initialisation des colonnes de la table de suivi des commandes
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCommande"));
        prepTimeColumn.setCellValueFactory(new PropertyValueFactory<>("tempsPreparation"));
        deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("tempsLivraison"));

        // Configuration de la colonne de confirmation
        confirmationColumn.setCellValueFactory(new PropertyValueFactory<>("confirmationLivraison"));
        confirmationColumn.setCellFactory(tc -> new TableCell<Commande, Boolean>() {
            @Override
            protected void updateItem(Boolean confirmation, boolean empty) {
                super.updateItem(confirmation, empty);
                setText(empty ? null : confirmation ? "Oui" : "Non");
            }
        });

        // Initialisation des données de la table de suivi des commandes
        ObservableList<Commande_Stat> commande = FXCollections.observableArrayList(
                new Commande_Stat("190220241512A", 20, 20, true),
                new Commande_Stat("190220242014B", 50, 15, false),
                new Commande_Stat("190220241612C", 30, 10, false),
                new Commande_Stat("190220241725D", 40, 20, true),
                new Commande_Stat("19022024E2025", 10, 15, false),
                new Commande_Stat("19022024F1953", 30, 10, false)
                // ... autres commandes
        );
        orderTrackingTable.setItems(commande);

        // Initialisation des colonnes de la table des plats populaires
        dishNameColumn.setCellValueFactory(new PropertyValueFactory<>("nomPlat"));
        orderCountColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCommandes"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("noteMoyenne"));

        // Initialisation des données de la table des plats populaires
        ObservableList<PlatPopulaire> platsPopulaires = FXCollections.observableArrayList(
                new PlatPopulaire("Pizza Margherita", 150, 4.5),
                new PlatPopulaire("Lasagne", 90, 4.6),
                new PlatPopulaire("Steak", 200, 4.7),
                new PlatPopulaire("Sandwich La Marmite", 400, 4.5),
                new PlatPopulaire("Poutine", 50, 4.7),
                new PlatPopulaire("Salade riche", 500, 4.7)
                // ... autres plats
        );
        popularDishesTable.setItems(platsPopulaires);

        // Configuration du PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        platsPopulaires.forEach(plat -> pieChartData.add(new PieChart.Data(plat.getNomPlat(), plat.getNombreCommandes())));
        pieChart.setPrefSize(300, 300);
        pieChart.setData(pieChartData);
    }

    // Méthode pour retourner à l'interface de gestion
    public void Bouttonderetour (ActionEvent Cliquer) throws IOException{
        Stage stage = (Stage) ((Node) Cliquer.getSource()).getScene().getWindow();
        stage.close(); // Fermer l'interface actuelle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_Gestion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 294);
        stage.setScene(scene);
        stage.show();
    }
}
