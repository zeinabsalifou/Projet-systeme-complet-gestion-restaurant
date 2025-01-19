package tp1.interfacedevoire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Maincommande extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charge le fichier FXML de la vue de commande
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("commande-view.fxml"));
        Parent root = fxmlLoader.load();

        // Crée une scène avec la racine chargée et définit sa taille
        Scene scene = new Scene(root, 900, 730); // Ici, vous définissez la taille de la fenêtre

        // Définit le titre de la fenêtre
        primaryStage.setTitle("Passer une commande");

        // Charge l'icône de la fenêtre
        Image icon = new Image("C:\\Users\\rayan\\IdeaProjects\\interfacedevoire\\src\\main\\resources\\tp1\\interfacedevoire\\flame_icon_151350.png");
        primaryStage.getIcons().add(icon);

        // Définit la scène sur la fenêtre principale et l'affiche
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lance l'application JavaFX
        launch(args);
    }
}
