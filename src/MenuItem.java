package tp1.interfacedevoire;

public class MenuItem {
    private String title;       // Titre de l'élément du menu
    private String price;       // Prix de l'élément du menu
    private String imagePath;   // Chemin d'accès à l'image de l'élément du menu

    // Constructeur pour créer un nouvel élément de menu avec un titre, un prix et un chemin d'accès à l'image
    public MenuItem(String title, String price, String imagePath) {
        this.title = title;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Méthode pour récupérer le titre de l'élément du menu
    public String getTitle() {
        return title;
    }

    // Méthode pour récupérer le prix de l'élément du menu
    public String getPrice() {
        return price;
    }

    // Méthode pour récupérer le chemin d'accès à l'image de l'élément du menu
    public String getImagePath() {
        return imagePath;
    }
}
