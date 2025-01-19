package tp1.interfacedevoire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LaMarmite extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Charger le fichier FXML de l'interface de caisse
        FXMLLoader fxmlLoader = new FXMLLoader(LaMarmite.class.getResource("caisse.fxml"));
        // Créer une scène avec le contenu chargé à partir du fichier FXML
        Scene scene = new Scene(fxmlLoader.load());
        // Définir le titre de la fenêtre
        stage.setTitle("Caisse - Transaction de vente");
        // Charger l'icône de l'application
        Image icon = new Image("C:\\Users\\rayan\\IdeaProjects\\interfacedevoire\\src\\main\\resources\\tp1\\interfacedevoire\\flame_icon_151350.png");
        stage.getIcons().add(icon);
        // Définir la scène sur la fenêtre et afficher la fenêtre
        stage.setScene(scene);
        stage.show();
        // Désactiver la possibilité de redimensionner la fenêtre
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch();
    }
}
