package tp1.interfacedevoire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;

public class StocksMainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement de l'interface utilisateur à partir du fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/tp1/interfacedevoire/com/example/demo15/stocks-view.fxml"));

        // Définition du titre de la fenêtre principale
        primaryStage.setTitle("Suivi dynamique des stocks");

        // Création d'une nouvelle scène avec la racine (interface utilisateur) et dimensions spécifiées
        primaryStage.setScene(new Scene(root, 1500, 800));

        // Affichage de la fenêtre principale
        primaryStage.show();

        // Chargement de l'icône depuis le dossier de ressources "images"
        Image flame = new Image(getClass().getResourceAsStream("flame_icon_151350.png"));

        // Ajout de l'icône à la barre de titre de la fenêtre
        primaryStage.getIcons().add(flame);
    }

    public static void main(String[] args) {
        // Lancement de l'application JavaFX
        launch(args);
    }
}
