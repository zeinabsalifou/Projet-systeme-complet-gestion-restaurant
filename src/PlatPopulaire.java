package tp1.interfacedevoire;

public class PlatPopulaire {
    private String nomPlat;       // Nom du plat populaire
    private int nombreCommandes;  // Nombre de commandes du plat populaire
    private double noteMoyenne;   // Note moyenne du plat populaire

    // Constructeur pour créer un nouvel objet PlatPopulaire avec un nom, un nombre de commandes et une note moyenne
    public PlatPopulaire(String nomPlat, int nombreCommandes, double noteMoyenne) {
        this.nomPlat = nomPlat;
        this.nombreCommandes = nombreCommandes;
        this.noteMoyenne = noteMoyenne;
    }

    // Méthodes getters pour obtenir les attributs de l'objet PlatPopulaire
    public String getNomPlat() { return nomPlat; }
    public int getNombreCommandes() { return nombreCommandes; }
    public double getNoteMoyenne() { return noteMoyenne; }

    // Méthodes setters pour définir les attributs de l'objet PlatPopulaire
    public void setNomPlat(String nomPlat) { this.nomPlat = nomPlat; }
    public void setNombreCommandes(int nombreCommandes) { this.nombreCommandes = nombreCommandes; }
    public void setNoteMoyenne(double noteMoyenne) { this.noteMoyenne = noteMoyenne; }

    // Méthode toString pour afficher les informations de l'objet PlatPopulaire
    @Override
    public String toString() {
        return nomPlat + " - Commandes: " + nombreCommandes + ", Note: " + String.format("%.2f", noteMoyenne);
    }
}
