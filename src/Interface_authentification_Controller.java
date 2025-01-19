package tp1.interfacedevoire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Interface_authentification_Controller {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label errorLabel;

    public void initialize() {
        entree(); // Appel de la méthode entree lors de l'initialisation du contrôleur
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        // Vérifier si les champs ne sont pas vides et correspondent à des valeurs spécifiques
        if (!username.isEmpty() && !password.isEmpty() && username.equals("Gestionnaire") && password.equals("Gest@&124")) {
            // Charger une nouvelle interface
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.close(); // Fermer l'interface actuelle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_Gestion.fxml"));
            Parent root = loader.load();
            stage.setTitle("Menu - Menu du gestionnaire");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (!username.isEmpty() && !password.isEmpty() && username.equals("Caisse") && password.equals("Caisse@&12")) {
            // Charger une nouvelle interface
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.close(); // Fermer l'interface actuelle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("caisse.fxml"));
            Parent root = loader.load();
            stage.setTitle("Caisse - Transaction de vente");
            stage.setScene(new Scene(root));
            stage.show();
        } else if (!username.isEmpty() && !password.isEmpty() && username.equals("Commande") && password.equals("Commande@&124")) {
            // Charger une nouvelle interface
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.close(); // Fermer l'interface actuelle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("commande-view.fxml"));
            Parent root = loader.load();
            stage.setTitle("Commande - Traitement de commande");
            stage.setScene(new Scene(root));
            stage.show();
        }  else if (username.isEmpty() && password.isEmpty()) {
            // Afficher un message d'erreur
            errorLabel.setText("veuillez donner un nom d'utilisateur et mot de passe");
        } else if (username.isEmpty()){
            // Afficher un message d'erreur
            errorLabel.setText("veuillez donner un nom d'utilisateur");
        } else if (password.isEmpty()) {
            // Afficher un message d'erreur
            errorLabel.setText("veuillez donner un mot de passe");
        }else {
            // Afficher un message d'erreur
            errorLabel.setText("Identifiant ou mot de passe incorrect");
        }
    }

    public void entree() {
        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    onHelloButtonClick(); // Méthode de connexion à appeler lorsque la touche "Entrée" est pressée
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer l'exception IOException
                }
            }
        });

        passwordTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    onHelloButtonClick(); // Méthode de connexion à appeler lorsque la touche "Entrée" est pressée
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer l'exception IOException
                }
            }
        });
    }
}