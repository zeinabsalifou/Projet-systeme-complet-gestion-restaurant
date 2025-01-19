package tp1.interfacedevoire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FournisseursController {

    @FXML
    private TableView<Supplier> tableView5;

    @FXML
    private TableColumn<Supplier, Integer> idColumn;

    @FXML
    private TableColumn<Supplier, String> nomColumn;

    @FXML
    private TableColumn<Supplier, String> typeProduitsColumn;

    @FXML
    private TableColumn<Supplier, String> contactColumn;

    @FXML
    private TableColumn<Supplier, String> adresseColumn;

    @FXML
    private TableColumn<Supplier, String> numeroTelColumn;

    @FXML
    private TableColumn<Supplier, String> emailColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private Button  btnPrecede;


        public void initialize() {

            nomColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            typeProduitsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            contactColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            adresseColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            numeroTelColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
            emailColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

            loadFromFile(); //méthode pour charger les données depuis le fichier

            // gestionnaires d'événements pour détecter les modifications
            nomColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setNom(event.getNewValue());
                saveToFile(); // Enregistrer les modifications dans le fichier
            });

            typeProduitsColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setTypeProduits(event.getNewValue());
                saveToFile();
            });

            contactColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setContact(event.getNewValue());
                saveToFile();
            });

            adresseColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setAdresse(event.getNewValue());
                saveToFile();
            });

            numeroTelColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setNumeroTel(event.getNewValue());
                saveToFile();
            });

            emailColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setEmail(event.getNewValue());
                saveToFile();
            });

            idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            idColumn.setOnEditCommit(event -> {
                Supplier supplier = event.getRowValue();
                supplier.setId(Integer.parseInt(String.valueOf(event.getNewValue())));
                saveToFile();
            });

            // Ajouter un fournisseur
            Button addButton = new Button("Ajouter Fournisseur");
            addButton.setOnAction(event -> ajouterFournisseur());

            // Supprimer un fournisseur
            Button deleteButton = new Button("Supprimer Fournisseur");
            deleteButton.setOnAction(event -> supprimerFournisseur());

            btnPrecede.setOnAction(this::retourInterfaceF);
        }

        @FXML
        private void ajouterFournisseur() {
            // Créer un nouveau fournisseur avec des valeurs par défaut
            Supplier nouveauFournisseur = new Supplier(0, " ", " ", " ", " ", " ", " ");

            // Ajouter le fournisseur à la TableView
            tableView5.getItems().add(nouveauFournisseur);

            TableView.TableViewFocusModel<Supplier> focusModel = tableView5.getFocusModel();

            // Vérifier si une cellule est sélectionnée
            if (focusModel != null) {
                TablePosition<Supplier, ?> focusedCell = focusModel.getFocusedCell();
                if (focusedCell != null) {
                    // Appliquer le style directement à la ligne de la cellule active
                    int rowIndex = focusedCell.getRow();
                    tableView5.setRowFactory(tv -> new TableRow<Supplier>() {
                        @Override
                        public void updateItem(Supplier item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty && getIndex() == rowIndex) {
                                // Appliquer le style CSS pour le contour bleu
                                setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
                            } else {
                                // Réinitialiser le style pour les autres lignes
                                setStyle(null);
                            }
                        }
                    }
                    );
                }
            }

            // Enregistrer les modifications dans le fichier
            saveToFile();
        }

        @FXML
        private void supprimerFournisseur() {
            // le fournisseur sélectionné dans la TableView
            Supplier selectedSupplier = (Supplier) tableView5.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                //  confirmation pour la suppression
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Voulez-vous vraiment supprimer ce fournisseur?");
                alert.setContentText(selectedSupplier.toString());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // supprimer le fournisseur de la TableView et enregistrez les modifications
                    tableView5.getItems().remove(selectedSupplier);
                    saveToFile();
                }
            } else {
                // Afficher un message si aucun fournisseur n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText("Aucun fournisseur sélectionné");
                alert.setContentText("Veuillez sélectionner un fournisseur à supprimer.");
                alert.showAndWait();
            }
        }


    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("fournisseurs.txt"))) {
            String line;
            List<Supplier> suppliers = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                suppliers.add(new Supplier(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
            }
            tableView5.getItems().setAll(suppliers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("fournisseurs.txt"))) {
                for (Supplier supplier : tableView5.getItems()) {
                    writer.write(supplier.getId() + "," + supplier.getNom() + "," + supplier.getTypeProduits() + "," +
                            supplier.getContact() + "," + supplier.getAdresse() + "," + supplier.getNumeroTel() + "," +
                            supplier.getEmail() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void retourInterfaceF(ActionEvent actionEvent) {
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
    }


    public class Supplier {
            private int id;
            private String nom;
            private String typeProduits;
            private String contact;
            private String adresse;
            private String numeroTel;
            private String email;

            public Supplier(int id, String nom, String typeProduits, String contact, String adresse, String numeroTel, String email) {
                this.id = id;
                this.nom = nom;
                this.typeProduits = typeProduits;
                this.contact = contact;
                this.adresse = adresse;
                this.numeroTel = numeroTel;
                this.email = email;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNom() {
                return nom;
            }

            public void setNom(String nom) {
                this.nom = nom;
            }

            public String getTypeProduits() {
                return typeProduits;
            }

            public void setTypeProduits(String typeProduits) {
                this.typeProduits = typeProduits;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getAdresse() {
                return adresse;
            }

            public void setAdresse(String adresse) {
                this.adresse = adresse;
            }

            public String getNumeroTel() {
                return numeroTel;
            }

            public void setNumeroTel(String numeroTel) {
                this.numeroTel = numeroTel;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            @Override
            public String toString() {
                return "Supplier{" +
                        "id=" + id +
                        ", nom='" + nom + '\'' +
                        ", typeProduits='" + typeProduits + '\'' +
                        ", contact='" + contact + '\'' +
                        ", adresse='" + adresse + '\'' +
                        ", numeroTel='" + numeroTel + '\'' +
                        ", email='" + email + '\'' +
                        '}';
            }
        }
}
