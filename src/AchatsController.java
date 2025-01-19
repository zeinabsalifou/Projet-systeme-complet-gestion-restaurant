package tp1.interfacedevoire;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Cette classe est le contrôleur pour la vue Achats.fxml
public class AchatsController implements Initializable {

    // Les champs annotés avec @FXML sont injectés par JavaFX lors du chargement de la vue FXML
    @FXML
    private TextField numerocarte;
    @FXML
    private TextField dateexpiration;
    @FXML
    private TextField codesecurite;
    @FXML
    private TextField nomproduit;
    @FXML
    private TextField quantites;
    @FXML
    private TextField prixunitaire;
    @FXML
    private ComboBox<String> nomFournisseurComboBox;
    @FXML
    private Label totallabel;
    @FXML
    private Label messagelabel;
    @FXML
    private Button validerbutton;
    @FXML
    private Label messageslabel;
    @FXML
    private Button validation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation des champs et des listeners lors du chargement de la vue

        // Ajout de listeners pour recalculer le total lors de la modification des champs de quantité et de prix unitaire
        quantites.textProperty().addListener((observable, oldValue, newValue) -> {
            calculerTotal();
        });
        prixunitaire.textProperty().addListener((observable, oldValue, newValue) -> {
            calculerTotal();
        });

        // Gestionnaire de changement de texte pour activer/désactiver le bouton de validation en fonction du contenu des champs
        ChangeListener<String> textChangeListener = (observable, oldValue, newValue) -> {
            validation.setDisable(
                    nomproduit.getText().isEmpty() ||
                            quantites.getText().isEmpty() ||
                            prixunitaire.getText().isEmpty()
            );
        };

        // Ajout du gestionnaire aux champs de texte
        nomproduit.textProperty().addListener(textChangeListener);
        quantites.textProperty().addListener(textChangeListener);
        prixunitaire.textProperty().addListener(textChangeListener);

        // Désactiver le bouton au départ si un champ est vide
        validation.setDisable(
                nomproduit.getText().isEmpty() ||
                        quantites.getText().isEmpty() ||
                        prixunitaire.getText().isEmpty()
        );
    }

    // Méthode appelée pour calculer le total à payer
    @FXML
    private void calculerTotal() {
        String quantiteStr = quantites.getText();
        String prixUnitaireStr = prixunitaire.getText();

        if (!quantiteStr.isEmpty() && !prixUnitaireStr.isEmpty()) {
            try {
                int quantite = Integer.parseInt(quantiteStr);
                double prixUnitaire = Double.parseDouble(prixUnitaireStr);
                double total = quantite * prixUnitaire;
                totallabel.setText("Total à payer: " + total + "$");
            } catch (NumberFormatException e) {
                totallabel.setText(""); // Affichage vide en cas d'erreur de conversion
            }
        } else {
            totallabel.setText(""); // Affichage vide si l'un des champs est vide
        }
    }

    // Méthode appelée lors du clic sur le bouton "Payer"
    public void payerButtonClicked() {
        try {
            // Chargement du formulaire de carte de crédit dans une nouvelle fenêtre modale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cartecredit.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Formulaire de Carte de Crédit");
            stage.setScene(new Scene(root));

            stage.showAndWait();

            // Réinitialisation des champs après la fermeture du formulaire de carte de crédit
            reinitialiserButtonClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode appelée lors du clic sur le bouton "Réinitialiser"
    @FXML
    public void reinitialiserButtonClicked() {
        // Effacement des champs et des labels
        nomproduit.clear();
        quantites.clear();
        prixunitaire.clear();
        totallabel.setText("");
        messageslabel.setText("");
    }

    // Méthode appelée lors du clic sur le bouton "Valider"
    @FXML
    private void validerButtonClicked(ActionEvent event) {
        // Vérification si tous les champs de la carte de crédit sont remplis
        if (numerocarte.getText().isEmpty() || dateexpiration.getText().isEmpty() || codesecurite.getText().isEmpty()) {
            // Affichage d'un message d'erreur si des champs sont vides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs avant de valider.");
            alert.showAndWait();
        } else {
            // Affichage d'une confirmation de paiement
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement effectué");
            alert.setHeaderText(null);
            alert.setContentText("Paiement effectué avec succès!");

            // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
            alert.showAndWait();

            // Fermeture de la fenêtre actuelle
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    // Méthode pour afficher un message dans le label
    private void setMessage(String message) {
        messagelabel.setText(message);
    }

    // Méthode appelée lors du clic sur le bouton "Réinitialiser"
    @FXML
    public void reinitialiseButtonClicked() {
        // Effacement des champs de la carte de crédit
        numerocarte.clear();
        dateexpiration.clear();
        codesecurite.clear();
    }

    // Méthode appelée lors du clic sur le bouton "Retour"
    public void retourInterfaceA(ActionEvent actionEvent) {
        // Fermeture de la fenêtre actuelle
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
