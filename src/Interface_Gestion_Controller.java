package tp1.interfacedevoire;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface_Gestion_Controller {
    // Méthode pour gérer le clic sur le bouton "Stock"
    public void bouttton_stock(ActionEvent cliquer) throws IOException {
        // Récupérer la fenêtre actuelle et la fermer
        Stage stage = (Stage) ((Node) cliquer.getSource()).getScene().getWindow();
        stage.close();

        // Charger une nouvelle interface (stocks-view.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tp1/interfacedevoire/com/example/demo15/stocks-view.fxml"));
        Parent root = loader.load();
        stage.setTitle("Gestionnaire - Gestion des Stocks");
        stage.setScene(new Scene(root,1500,800));
        stage.show();
    }

    // Méthode pour gérer le clic sur le bouton "Statistique"
    public void Boutton_Statistique(ActionEvent actionEvent) throws IOException {
        // Récupérer la fenêtre actuelle et la fermer
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        // Charger une nouvelle interface (Statistiques.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistiques.fxml"));
        Parent root = loader.load();
        stage.setTitle("Gestionnaire - Gestion de statistiques");
        Scene scene = new Scene(root, 800, 780);
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour gérer le clic sur le bouton "Déconnexion"
    public void Bouttondeconnexion(ActionEvent actionEvent) throws IOException {
        // Récupérer la fenêtre actuelle et la fermer
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        // Charger l'interface d'authentification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_authentification.fxml"));
        Parent root = loader.load();
        stage.setTitle("Page d'authentification");
        Scene scene = new Scene(root, 771, 417);
        stage.setScene(scene);
        stage.show();
    }
}
