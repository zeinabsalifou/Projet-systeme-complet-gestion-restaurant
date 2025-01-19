package tp1.interfacedevoire;

/*
 *       Auteur: Amélie Cantin
 *       22 mars 2024
 *
 *       Pour l'équipe no 1:
 *               Amélie Cantin
 *               Rayane Dakech
 *               Soukaina Qaraqouna
 *               Assia Rabahi
 *               Zeinab Salifou
 *
 *       Travail Pratique dans le cadre du cours
 *       Introduction aux interfaces utilisateur
 *                   INF1034
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class CaisseController implements Initializable {
    private static ObservableList<Commande> listeCommandes = FXCollections.observableArrayList();
    @FXML
    private ListView<Button> listeViewDesCommandes;
    private ObservableList<Button> listeDesBoutonsDeCommandes = FXCollections.observableArrayList();
    @FXML
    private TableView<Item> commandeTableView;
    @FXML
    private TableColumn<Item, String> itemColonne;
    @FXML
    private TableColumn<Item, Double> prixColonne;
    @FXML
    private Button ajouterModifierCommandeBouton;
    @FXML
    private Label commandeEnPaiementLabel;
    @FXML
    private  Button deconnecterBouton;
    @FXML
    private Label sousTotalLabel;
    @FXML
    private Label tpsLabel;
    @FXML
    private Label tvqLabel;
    @FXML
    private Label rabaisLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Button carteFideliteBouton;
    @FXML
    private Button rabaisBouton;
    @FXML
    private Button debitBouton;
    @FXML
    private Button creditBouton;
    @FXML
    private Label totalDroiteLabel;
    @FXML
    private Button payerBouton;
    @FXML
    private Button carteCadeauBouton;
    @FXML
    private Button argentBouton;

    private BoutonCommande boutonDeCommandeEnCours = null;

    //Commande commande;
    public CaisseController() {
    }

    /* **********************
     *       INITIALIZE
     ************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creerCommandesInit();
        listeCommandes.addAll(GestionCommandes.getListeCommandes());
        for (Commande commande : listeCommandes) {
            creerBoutonCommande(commande);
        }
        remplirListViewDesCommandes();
        desactiverBoutonsPaiement();
        setMnemonic();
        reactionBoutonAjouterModifierCommande();
    }

    private void creerCommandesInit() {
        Commande commande1 = DonneesTest.creerCommande1();
        GestionCommandes.ajouterCommande(commande1);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Commande commande2 = DonneesTest.creerCommande2();
        GestionCommandes.ajouterCommande(commande2);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Commande commande3 = DonneesTest.creerCommande3();
        GestionCommandes.ajouterCommande(commande3);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Commande commande4 = DonneesTest.creerCommande4();
        GestionCommandes.ajouterCommande(commande4);
    }

    private void remplirListViewDesCommandes() {
        listeViewDesCommandes.setItems(listeDesBoutonsDeCommandes);
        int taille = listeDesBoutonsDeCommandes.size();
        for (int i = taille ; i < 8 ;i++) {
            Button boutonVide = new Button();
            boutonVide.getStyleClass().add("boutonCommande");
            boutonVide.setDisable(true);
            listeDesBoutonsDeCommandes.add(boutonVide);
        }
    }

    private void setMnemonic() {
        ajouterModifierCommandeBouton.setMnemonicParsing(true);
        ajouterModifierCommandeBouton.setText("Ajouter / _Modifier Commande");
        //deconnecterBouton.setMnemonicParsing(true);
        //deconnecterBouton.setText("Déconnec_ter");
        carteFideliteBouton.setMnemonicParsing(true);
        carteFideliteBouton.setText("Carte _Fidélité");
        rabaisBouton.setMnemonicParsing(true);
        rabaisBouton.setText("_Rabais");
        debitBouton.setMnemonicParsing(true);
        debitBouton.setText("_Débit");
        creditBouton.setMnemonicParsing(true);
        creditBouton.setText("_Crédit");
        argentBouton.setMnemonicParsing(true);
        argentBouton.setText("_Argent $");
        carteCadeauBouton.setMnemonicParsing(true);
        carteCadeauBouton.setText("Carte ca_deau");
        payerBouton.setMnemonicParsing(true);
        payerBouton.setText("$  _PAYER" );
    }


    /* **********************
     *       GAUCHE
     ************************/

    private void reactionBoutonAjouterModifierCommande() {
        ajouterModifierCommandeBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Charger une nouvelle interface
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close(); // Fermer l'interface actuelle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("commande-view.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(new Scene(root,800 ,750));
                stage.show();
                ajouterModifierCommandeBouton.setUnderline(true);
            }
        });
    }

    private void creerBoutonCommande(Commande commande) {
        BoutonCommande boutonCommande = new BoutonCommande(commande);
        boutonCommande.setCouleurLabel2(etatCommandeCouleur(commande));
        boutonCommande.setId(commande.getNO_COMMANDE());
        if (commande.getEtatCommande().equals(EtatCommande.SERVICE_COMPLETE)) {
            boutonCommande.setDisable(false);
        }
        boutonCommande.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onCommandeClique(actionEvent);
                afficherEtatCommande(actionEvent);
            }
        });
        listeDesBoutonsDeCommandes.add(boutonCommande);
    }

    private Color etatCommandeCouleur(Commande commande) {
        Color couleur = Color.BLACK;
        if (commande.getEtatCommande().equals(EtatCommande.SERVICE_COMPLETE)) {
            couleur = Color.RED;
        } else if (commande.getEtatCommande().equals(EtatCommande.PRETE_A_LIVRER)) {
            couleur = Color.GREEN;
        }
        return couleur;
    }

    private static Button b = new Button();
    private void afficherBordureVerte(ActionEvent event) {
        Object o = event.getSource();
        if (o instanceof Button) {
            if (o != b) {
                b.setStyle("-fx-border-color: GREY; -fx-border-width: 1px");
                ((Button)o).setStyle("-fx-border-color: GREEN; -fx-border-width: 5px");
                b = (Button) o;
            }
        }
    }

    private static Commande c = new Commande(TypeCommande.LIVRAISON);
    private static BoutonCommande bc = new BoutonCommande(c);
    private void afficherEtatCommande(ActionEvent event) {
        Object o = event.getSource();
        if (o instanceof BoutonCommande) {
            Commande commande = ((BoutonCommande)o).getCommande();
            if (o != bc) {
                bc.getCommande().setEtatCommande(EtatCommande.SERVICE_COMPLETE);
                bc.setTextLabel2(EtatCommande.SERVICE_COMPLETE.getEtatCommandeEnum());
                bc.setCouleurLabel2(etatCommandeCouleur(commande));
                ((BoutonCommande)o).getCommande().setEtatCommande(EtatCommande.TRANSACTION_EN_COURS);
                ((BoutonCommande)o).setTextLabel2(EtatCommande.TRANSACTION_EN_COURS.getEtatCommandeEnum());
                ((BoutonCommande)o).setCouleurLabel2(etatCommandeCouleur(commande));
                bc = (BoutonCommande)o;
            }
        }

    }


    private ObservableList<Item> getListeItems(Commande commande) {
        ObservableList<Item> liste = FXCollections.observableArrayList();
        liste.setAll(commande.getListeItems());
        return liste;
    }

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

    private void onCommandeClique(ActionEvent event) {
        Object o = event.getSource();
        if (o instanceof BoutonCommande) {
            boutonDeCommandeEnCours = (BoutonCommande) o;
        }
        afficherBordureVerte(event);
        for (Button boutonCommande:listeDesBoutonsDeCommandes) {
            if (boutonCommande instanceof BoutonCommande) {
                if (!boutonCommande.equals(o)) {
                    boutonCommande.setStyle("-fx-border-color: GREY; -fx-border-width: 1px");
                }
            }
        }
        Commande commande = boutonDeCommandeEnCours.commande;
        commandeEnPaiementLabel.setText(boutonDeCommandeEnCours.getLabel1());
        itemColonne.setCellValueFactory(cellData -> cellData.getValue().getNom());
        prixColonne.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
        commandeTableView.setItems(getListeItems(commande));
        prixColonne.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        afficherCalculTotal(commande);
        activerBoutonsChoixPaiement();
        payerBouton.setDisable(true);
    }

    private void commandeTerminee() {
        commandeEnPaiementLabel.setText("");
        itemColonne.setCellValueFactory(cellData -> cellData.getValue().getNom());
        prixColonne.setCellValueFactory(cellData -> cellData.getValue().getPrix().asObject());
        commandeTableView.setItems(null);
        prixColonne.setCellFactory(tc -> new TableCell<Item, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        sousTotalLabel.setText("");
        tpsLabel.setText("");
        tvqLabel.setText("");
        totalLabel.setText("");
        totalDroiteLabel.setText("");
        desactiverBoutonsPaiement();
        payerBouton.setDisable(true);
    }

    public void sedeconnecter(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_authentification.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 771, 417);
        stage.setScene(scene);
        stage.show();
    }
    /* **********************
     *       CENTRE
     ************************/

    private void afficherCalculTotal(Commande commande) {
        sousTotalLabel.setText(currencyFormat.format(commande.sousTotal()));
        tpsLabel.setText(currencyFormat.format(commande.tps(commande.sousTotal())));
        tvqLabel.setText(currencyFormat.format(commande.tvq(commande.sousTotal())));
        totalLabel.setText(currencyFormat.format(commande.total()));
        totalDroiteLabel.setText(currencyFormat.format(commande.total()));
    }


    /* **********************
     *       DROITE
     ************************/

    private void desactiverBoutonsPaiement() {
        carteFideliteBouton.setDisable(true);
        rabaisBouton.setDisable(true);
        debitBouton.setDisable(true);
        creditBouton.setDisable(true);
        argentBouton.setDisable(true);
        carteCadeauBouton.setDisable(true);
        payerBouton.setDisable(true);
    }

    private void activerBoutonsChoixPaiement() {
        carteFideliteBouton.setDisable(false);
        reactionBoutonFidelite();
        rabaisBouton.setDisable(false);
        reactionBoutonRabais();
        debitBouton.setDisable(false);
        creditBouton.setDisable(false);
        argentBouton.setDisable(false);
        carteCadeauBouton.setDisable(false);
        reactionChoixPaiement();
    }

    private void reactionBoutonFidelite() {
        carteFideliteBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog popup = new Dialog();
                Stage stage = (Stage) popup.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(LaMarmite.class.getResourceAsStream("flame_icon_151350.png")));
                popup.setContentText("\nVers l'interface du programme de fidélité...\n(interface non développée dans le cadre de ce travail)");
                popup.setTitle("Programme de fidélité");
                ButtonType bouton = new ButtonType("Continuer");
                popup.getDialogPane().getButtonTypes().add(bouton);
                Optional<ButtonType> reponse = popup.showAndWait();
                if (reponse.isPresent()) {
                    ButtonType valeurReponse = reponse.get();
                }
            }
        });
    }

    private void reactionBoutonRabais() {
        rabaisBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog popup = new Dialog();
                Stage stage = (Stage) popup.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(LaMarmite.class.getResourceAsStream("flame_icon_151350.png")));
                popup.setContentText("\nVers l'interface permettant d'appliquer des rabais...\n(interface non développée dans le cadre de ce travail)");
                popup.setTitle("Rabais");
                ButtonType bouton = new ButtonType("Continuer");
                popup.getDialogPane().getButtonTypes().add(bouton);
                Optional<ButtonType> reponse = popup.showAndWait();
                if (reponse.isPresent()) {
                    ButtonType valeurReponse = reponse.get();
                    rabaisLabel.setText(currencyFormat.format(0));
                }
            }
        });
    }

    private void reactionChoixPaiement() {
        reactionBoutonDebit();
        reactionBoutonCredit();
        reactionBoutonArgent();
        reactionBoutonCarteCadeau();
    }

    private void reactionBoutonDebit() {
        debitBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModeDePaiement modeDePaiement = choisirModePaiement(event);
                if (modeDePaiement != null) {
                    payerBouton.setDisable(false);
                    boutonDeCommandeEnCours.setStyle("-fx-border-color: GREEN; -fx-border-width: 5px");
                    listeViewDesCommandes.refresh();
                    String titre = "Paiement par carte de débit";
                    String texte = "\n\nSuivre les instructions sur le terminal de paiement...\n(interface suivante non développée dans le cadre de ce travail)";
                    reactionBoutonPayer(titre, texte);
                }
            }
        });
    }

    private void reactionBoutonCredit() {
        creditBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModeDePaiement modeDePaiement = choisirModePaiement(event);
                if (modeDePaiement != null) {
                    payerBouton.setDisable(false);
                    Commande commande = boutonDeCommandeEnCours.getCommande();
                    commande.setEtatCommande(EtatCommande.TRANSACTION_EN_COURS);
                    boutonDeCommandeEnCours.setTextLabel2("Transaction en cours");
                    boutonDeCommandeEnCours.setCouleurLabel2(etatCommandeCouleur(commande));
                    boutonDeCommandeEnCours.setStyle("-fx-border-color: GREEN; -fx-border-width: 5px");
                    String titre = "Paiement par carte de crédit";
                    String texte = "\n\nSuivre les instructions sur le terminal de paiement...\n(interface suivante non développée dans le cadre de ce travail)";
                    reactionBoutonPayer(titre, texte);
                }
            }
        });
    }

    private void reactionBoutonArgent() {
        argentBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModeDePaiement modeDePaiement = choisirModePaiement(event);
                if (modeDePaiement != null) {
                    payerBouton.setDisable(false);
                    Commande commande = boutonDeCommandeEnCours.getCommande();
                    commande.setEtatCommande(EtatCommande.TRANSACTION_EN_COURS);
                    boutonDeCommandeEnCours.setTextLabel2("Transaction en cours");
                    boutonDeCommandeEnCours.setCouleurLabel2(etatCommandeCouleur(commande));
                    boutonDeCommandeEnCours.setStyle("-fx-border-color: GREEN; -fx-border-width: 5px");
                    String titre = "Paiement en argent";
                    String texte = "\n\nVers l'interface du paiement en argent...\n(interface non développée dans le cadre de ce travail)";
                    reactionBoutonPayer(titre, texte);
                }
            }
        });
    }

    private void reactionBoutonCarteCadeau() {
        carteCadeauBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModeDePaiement modeDePaiement = choisirModePaiement(event);
                if (modeDePaiement != null) {
                    payerBouton.setDisable(false);
                    carteCadeauBouton.setStyle("-fx-font-size: 25px;-fx-border-color: GREEN; -fx-border-width: 5px");
                    Commande commande = boutonDeCommandeEnCours.getCommande();
                    commande.setEtatCommande(EtatCommande.TRANSACTION_EN_COURS);
                    boutonDeCommandeEnCours.setTextLabel2("Transaction en cours");
                    boutonDeCommandeEnCours.setCouleurLabel2(etatCommandeCouleur(commande));
                    boutonDeCommandeEnCours.setStyle("-fx-border-color: GREEN; -fx-border-width: 5px");
                    String titre = "Paiement par carte cadeau";
                    String texte = "\n\nVers l'interface du paiement par carte cadeau...\n(interface non développée dans le cadre de ce travail)";
                    reactionBoutonPayer(titre, texte);
                }
            }
        });
    }

    private void reactionBoutonPayer(String titre, String texte) {
        payerBouton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog popup = new Dialog();
                Stage stage = (Stage) popup.getDialogPane().getScene().getWindow();
                Image icon = new Image("C:\\Users\\rayan\\IdeaProjects\\interfacedevoire\\src\\main\\resources\\tp1\\interfacedevoire\\flame_icon_151350.png");
                stage.getIcons().add(icon);
                popup.setContentText(texte);
                popup.setTitle(titre);
                ButtonType bouton = new ButtonType("Continuer");
                popup.getDialogPane().getButtonTypes().add(bouton);
                Optional<ButtonType>reponse = popup.showAndWait();
                if (reponse.isPresent()) {
                    popup.setContentText("Merci!");
                    popup.setTitle("Transaction approuvée");
                    reponse = popup.showAndWait();
                    if (reponse.isPresent()) {
                        Commande commande = boutonDeCommandeEnCours.getCommande();
                        listeCommandes.remove(commande);
                        listeDesBoutonsDeCommandes.remove(boutonDeCommandeEnCours);
                        listeViewDesCommandes.refresh();
                        commandeEnPaiementLabel.setText("");
                        commandeTerminee(); // Efface les infos du centre
                        rabaisLabel.setText("");
                    }
                }
            }
        });
    }

    private ModeDePaiement choisirModePaiement(ActionEvent event) {
        Object o = event.getSource();
        String id = "";
        if (o instanceof Button) {
            id = ((Button) o).getId();
        }
        afficherBordureVerte(event);
        switch (id) {
            case "debitBouton":
                return ModeDePaiement.DEBIT;
            case "creditBouton":
                return ModeDePaiement.CREDIT;
            case "argentBouton":
                return ModeDePaiement.ARGENT;
            case "carteCadeauBouton":
                return ModeDePaiement.CARTE_CADEAU;
        }
        return null;
    }
}