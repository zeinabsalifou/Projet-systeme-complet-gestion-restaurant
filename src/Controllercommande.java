package tp1.interfacedevoire;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controllercommande {
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox orderBox;
    private MenuItem item;
    private DoubleProperty subtotal = new SimpleDoubleProperty(0.0);
    @FXML
    private DoubleProperty tps = new SimpleDoubleProperty(0.0);
    @FXML
    private DoubleProperty tvq = new SimpleDoubleProperty(0.0);
    @FXML
    private DoubleProperty total = new SimpleDoubleProperty(0.0);
    @FXML
    private VBox itemsContainer; // Assurez-vous que c'est le VBox contenant les éléments de menu

    @FXML
    private TextField searchField;

    private List<VBox> allMenuItems;

    // Getters pour les propriétés
    public DoubleProperty subtotalProperty() {
        return subtotal;
    }

    public DoubleProperty tpsProperty() {
        return tps;
    }

    public DoubleProperty tvqProperty() {
        return tvq;
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    @FXML
    private Label labelSubtotal;
    @FXML
    private Label labelTps;
    @FXML
    private Label labelTvq;
    @FXML
    private Label labelTotal;
    @FXML
    private ImageView logoImageView;
    @FXML
    private GridPane menuGrid; // GridPane où les éléments de menu sont affichés
    private List<String> orderItemsList = new ArrayList<>();
    @FXML
    private GridPane mainMenuContainer;
    private Map<String, Integer> orderedItemsMap = new HashMap<>();
    private Map<String, List<MenuItem>> menuItemsByCategory;
    public void initialize() {

        labelSubtotal.textProperty().bind(Bindings.format("%.2f $", subtotal));
        labelTps.textProperty().bind(Bindings.format("%.2f $", tps));
        labelTvq.textProperty().bind(Bindings.format("%.2f $", tvq));
        labelTotal.textProperty().bind(Bindings.format("%.2f $", total));
        MenuItem[] menuItems = {
                new MenuItem("Salade legume", "10$", "img7.png"),
                new MenuItem("Salade verte", "5$", "img3.png"),
                new MenuItem("Salade riche", "12$", "img8.png"),
                new MenuItem("Petite feta", "8$", "img2.png"),
                new MenuItem("Feta moyenne", "10$", "img5.png"),
                new MenuItem("Salade Avocat", "20$", "img1.png"),
                new MenuItem("Salade crouton", "10$", "img4.png"),
                new MenuItem("Roquette", "5$", "img9.png"),
                new MenuItem("Crémeuse", "10$", "img10.png"),
                new MenuItem("Au tomate", "4$", "img11.png"),
                new MenuItem("Au choix", "10$", "img6.png"),
                new MenuItem("Nouilles", "3$", "img12.png"),
        };
        allMenuItems = new ArrayList<>();
        final int maxColumn = 4; // Le nombre maximum d'éléments par ligne
        int column = 0;
        int row = 0;

        for (MenuItem item : menuItems) {
            VBox menuItemBox = createMenuItemBox(item);
            allMenuItems.add(menuItemBox);
            mainMenuContainer.add(menuItemBox, column, row); // Ajoutez menuItemBox au GridPane
            column++;
            if (column == maxColumn) {
                column = 0;
                row++;
            }
        }

        Image logoImage = new Image(getClass().getResourceAsStream("logo.png"));
        logoImageView.setFitWidth(100); // ou la taille que vous souhaitez
        logoImageView.setPreserveRatio(true);
        logoImageView.setImage(logoImage);
        // Préparez les données pour chaque catégorie
        prepareMenuData();

        // Afficher les entrées par défaut
        showEntrees(null);
    }

    private VBox createMenuItemBox(MenuItem item) {
            // Créez le titre du menu et appliquez le style
            Label titleLabel = new Label(item.getTitle());
            titleLabel.getStyleClass().add("menu-title");
            titleLabel.setAlignment(Pos.CENTER); // Alignement du texte au centre

            // Créez le label de prix et appliquez le style
            Label priceLabel = new Label(item.getPrice());
            priceLabel.getStyleClass().add("menu-price");
            priceLabel.setAlignment(Pos.CENTER); // Alignement du texte au centre

            // Créez l'ImageView pour l'image du menu et définissez ses propriétés
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(getClass().getResourceAsStream(item.getImagePath()));
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imageView.setFitWidth(100); // Taille fixe en largeur
            imageView.setFitHeight(100); // Taille fixe en hauteur
        // Créez le spinner pour la quantité
        Spinner<Integer> spinner = new Spinner<>(0, 10, 1);
        spinner.setPrefWidth(50);

        // Créez le bouton d'ajout et appliquez le style
        Button addButton = new Button("Ajouter");
        addButton.getStyleClass().add("add-button");
        addButton.setOnAction(event -> addOrderItem(item, spinner.getValue()));

        // Créez le HBox pour contenir le spinner et le bouton d'ajout
        HBox hb = new HBox(10, spinner, addButton); // 10 est l'espacement entre les éléments
        hb.setAlignment(Pos.CENTER); // Centrez les éléments dans le HBox

        // Créez le VBox pour contenir tous les éléments ci-dessus
        VBox box = new VBox(5, titleLabel, imageView, priceLabel, hb); // 5 est l'espacement entre les éléments
        box.getStyleClass().add("menu-item-box");
        box.setAlignment(Pos.CENTER); // Alignez tous les éléments au centre

        return box;

    }

    @FXML
    private void searchMenuItems(ActionEvent event) {
        String searchText = searchField.getText().toLowerCase();
        List<VBox> filteredItems = allMenuItems.stream()
                .filter(item -> itemContainsText(item, searchText))
                .collect(Collectors.toList());
        updateMenuDisplay(filteredItems);
    }


    private boolean itemContainsText(VBox item, String searchText) {
        Label titleLabel = (Label) item.getChildren().get(0); // Supposant que le titre est le premier enfant
        return titleLabel.getText().toLowerCase().contains(searchText);
    }

    private void updateMenuDisplay(List<VBox> itemsToShow) {
        itemsContainer.getChildren().clear();
        itemsContainer.getChildren().addAll(itemsToShow);
    }

    public void addOrderItem(MenuItem item, int quantity) {
        // Mettez à jour la map avec la nouvelle quantité
        String itemName = item.getTitle();
        int currentQuantity = orderedItemsMap.getOrDefault(itemName, 0);
        orderedItemsMap.put(itemName, currentQuantity + quantity);

        // Mise à jour du total
        double itemPrice = Double.parseDouble(item.getPrice().replace("$", "").trim());
        double itemTotal = itemPrice * quantity;

        subtotal.set(subtotal.get() + itemTotal);
        tps.set(subtotal.get() * 0.05);
        tvq.set(subtotal.get() * 0.09975);
        total.set(subtotal.get() + tps.get() + tvq.get());

        // Mise à jour de l'affichage
        updateOrderItemsDisplay();
    }

    public void updateOrderItemsDisplay() {
        orderBox.getChildren().clear();
        for (Map.Entry<String, Integer> entry : orderedItemsMap.entrySet()) {
            String displayText = entry.getKey() + " x" + entry.getValue();
            Label itemLabel = new Label(displayText);
            itemLabel.getStyleClass().add("item-name");
            orderBox.getChildren().add(itemLabel);
        }
    }

    public void clearOrder(ActionEvent actionEvent) {
        // Efface les éléments de la commande et réinitialise la map
        orderedItemsMap.clear();
        updateOrderItemsDisplay();

        // Réinitialise les valeurs des propriétés
        subtotal.set(0.0);
        tps.set(0.0);
        tvq.set(0.0);
        total.set(0.0);
    }
    @FXML
    private void validateOrder(ActionEvent event) throws IOException {
        if(subtotal.get() > 0) {
            // Charger l'image de confirmation
            Image confirmationImage = new Image(getClass().getResourceAsStream("confirmation.png"));
            ImageView imageView = new ImageView(confirmationImage);
            // Définir la largeur et la hauteur souhaitées pour l'ImageView
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true); // Pour préserver le ratio de l'image lors du redimensionnement

            // Créer l'alerte de confirmation avec l'image
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de la commande");
            confirmationAlert.setHeaderText("Confirmation de la commande");
            confirmationAlert.setContentText("Votre commande a été validée avec succès!");
            confirmationAlert.setGraphic(imageView); // Définir l'image dans l'alerte

            // Afficher l'alerte et attendre une réponse
            confirmationAlert.showAndWait();
            // Charger une nouvelle interface
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close(); // Fermer l'interface actuelle

            FXMLLoader loader = new FXMLLoader(getClass().getResource("caisse.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1300, 804);
            stage.setScene(scene);
            stage.show();
        } else {
            // Afficher un message d'erreur si la commande est vide
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur de commande");
            errorAlert.setHeaderText("Commande vide");
            errorAlert.setContentText("Vous ne pouvez pas valider une commande vide.");
            errorAlert.showAndWait();
        }
    }

    public void updateOrderItemsDisplay(List<String> orderItems) {
        orderBox.getChildren().clear(); // Clear the existing items first
        for (String item : orderItems) {
            Label itemLabel = new Label(item);
            itemLabel.getStyleClass().add("item-name");
            orderBox.getChildren().add(itemLabel);
        }
    }

    // Prépare et organise les données de menu par catégorie
    private void prepareMenuData() {
        menuItemsByCategory = new HashMap<>();

        // Entrées
        List<MenuItem> entrees = new ArrayList<>();
        entrees.add(new MenuItem("Salade legume", "10$", "img7.png"));
        entrees.add(new MenuItem("Salade verte", "5$", "img3.png"));
        entrees.add(new MenuItem("Salade riche", "12$", "img8.png"));
        entrees.add(new MenuItem("Petite feta", "8$", "img2.png"));
        entrees.add(new MenuItem("Feta moyenne", "10$", "img5.png"));
        entrees.add(new MenuItem("Salade Avocat", "20$", "img1.png"));
        entrees.add(new MenuItem("Salade crouton", "10$", "img4.png"));
        entrees.add(new MenuItem("Roquette", "5$", "img9.png"));
        entrees.add(new MenuItem("Crémeuse", "10$", "img10.png"));
        entrees.add(new MenuItem("Au tomate", "4$", "img11.png"));
        entrees.add(new MenuItem("Au choix", "10$", "img6.png"));
        entrees.add(new MenuItem("Nouilles", "3$", "img12.png"));
        menuItemsByCategory.put("Entrées", entrees);

        // Plats principaux
        List<MenuItem> platsPrincipaux = new ArrayList<>();
        platsPrincipaux.add(new MenuItem("Steak Frites", "25$", "steack.png"));
        platsPrincipaux.add(new MenuItem("Poulet grillé", "20$", "poulet-grillé.png"));
        platsPrincipaux.add(new MenuItem("Poulet sauce au curry", "19$", "poulet-sauce.png"));
        platsPrincipaux.add(new MenuItem("Pizza italienne", "25$", "pizza.png"));
        platsPrincipaux.add(new MenuItem("Couscous Royal", "26$", "Couscous.png"));
        platsPrincipaux.add(new MenuItem("Couscous double ", "40$", "Couscous-doubleportion.png"));
        platsPrincipaux.add(new MenuItem("Saumon Poêlé", "22$", "saumon-croustillant.png"));
        platsPrincipaux.add(new MenuItem("Entrecôte Grillée", "25$", "entrecote-grillée.png"));
        platsPrincipaux.add(new MenuItem("Roti de porc", "19$", "roti-porc.png"));
        platsPrincipaux.add(new MenuItem("laniére de poulet", "20$", "laniere.png"));
        menuItemsByCategory.put("Plat principal", platsPrincipaux);

        // Boissons
        List<MenuItem> boissons = new ArrayList<>();
        boissons.add(new MenuItem("Jus d'orange", "4$", "orange.jpg"));
        boissons.add(new MenuItem("Jus de fraise", "4$", "fraise.png"));
        boissons.add(new MenuItem("coca cola", "3$", "coca.jpg"));
        boissons.add(new MenuItem("thé ", "4$", "thé1.png"));
        boissons.add(new MenuItem("Jus de melon", "3$", "melon.png"));
        boissons.add(new MenuItem("cocktail", "10$", "cocktail.png"));
        menuItemsByCategory.put("Boissons", boissons);
        //sandwichs
        List<MenuItem> sandwichs = new ArrayList<>();
        sandwichs.add(new MenuItem("Sandwich poulet", "15$", "poulet.png"));
        sandwichs.add(new MenuItem("Sandwich oeuf", "12$", "oeuf.png"));
        sandwichs.add(new MenuItem("Sandwich club", "10$", "clubimg.png"));
        sandwichs.add(new MenuItem("Sandwich jambon", "9$", "jambon.png"));
        sandwichs.add(new MenuItem("Sandwich saumon", "20$", "saumon.png"));
        sandwichs.add(new MenuItem("Sandwich Avocat", "14$", "avocat.png"));
        menuItemsByCategory.put("Sandwichs", sandwichs);
        //Menuenfants
        List<MenuItem> menue = new ArrayList<>();
        menue.add(new MenuItem("Plat 1", "10$", "plat1.png"));
        menue.add(new MenuItem("Plat 2", "11$", "plat2.png"));
        menue.add(new MenuItem("Plat 3", "12$", "plat3.png"));
        menue.add(new MenuItem("Plat 4", "9$", "plat4.png"));
        menue.add(new MenuItem("Plat 5", "10$", "plat5.png"));
        menuItemsByCategory.put("Menu enfants", menue);
    }

    // Méthodes de gestion des actions pour chaque catégorie de menu
    public void showEntrees(ActionEvent actionEvent) {
        displayMenuItems("Entrées");
    }

    public void showPlatsPrincipaux(ActionEvent actionEvent) {
        displayMenuItems("Plat principal");
    }

    public void showBoissons(ActionEvent actionEvent) {
        displayMenuItems("Boissons");
    }

    public void showSandwichs(ActionEvent actionEvent) {
        displayMenuItems("Sandwichs");
    }

    public void showMenuEnfants(ActionEvent actionEvent) {
        displayMenuItems("Menu enfants");
    }

    // Méthode pour afficher les éléments de menu pour une catégorie donnée
    private void displayMenuItems(String category) {
        List<MenuItem> items = menuItemsByCategory.get(category);

        if (items == null) {
            System.out.println("Aucune donnée pour la catégorie : " + category);
            return;
        }

        // Effacer les éléments précédents
        mainMenuContainer.getChildren().clear();

        int maxColumn = 4; // Nombre maximum d'éléments par ligne
        int column = 0;
        int row = 0;
        for (MenuItem item : items) {
            VBox menuItemBox = createMenuItemBox(item);
            mainMenuContainer.add(menuItemBox, column, row); // Ajoutez menuItemBox au GridPane
            column++;
            if (column == maxColumn) {
                column = 0;
                row++;
            }
        }
    }

    public void Bouttondeconnexion(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_authentification.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 771, 417);
        stage.setScene(scene);
        stage.show();
    }
}