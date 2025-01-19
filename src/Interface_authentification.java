package tp1.interfacedevoire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface_authentification extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du fichier FXML de l'interface d'authentification
        FXMLLoader fxmlLoader = new FXMLLoader(Interface_authentification.class.getResource("Interface_authentification.fxml"));
        // Création de la scène avec la taille spécifiée
        Scene scene = new Scene(fxmlLoader.load(), 771, 417);
        // Titre de la fenêtre
        stage.setTitle("Page d'authentification");
        // Chargement de l'icône de l'application
        Image icon = new Image("C:\\Users\\rayan\\IdeaProjects\\interfacedevoire\\src\\main\\resources\\tp1\\interfacedevoire\\flame_icon_151350.png");
        stage.getIcons().add(icon);
        // Ajout de la scène à la fenêtre et affichage de la fenêtre
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Lancement de l'application JavaFX
        launch();
    }
}
