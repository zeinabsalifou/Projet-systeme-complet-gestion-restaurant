package tp1.interfacedevoire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Chargez le fichier FXML des statistiques et créez la scène.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Statistiques.fxml")); // Assurez-vous que le nom de fichier correspond à votre fichier FXML.
        Scene scene = new Scene(fxmlLoader.load(), 880, 780);
        stage.setTitle("Gestion des commandes et plats");

        // Ajoutez l'icône de la fenêtre.
        Image icon = new Image(getClass().getResourceAsStream("flame_icon_151350.png")); // Assurez-vous que le chemin d'accès est correct.
        stage.getIcons().add(icon);

        // Mettez en place la scène et affichez-la.
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Lancez l'application JavaFX.
        launch();
    }
}
