package tp1.interfacedevoire;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

// Cette classe représente une commande passée par un client
public class Commande {

    // Attributs
    private final String NO_COMMANDE; // Numéro unique de la commande
    private ArrayList<Item> listeItems; // Liste des articles commandés
    private TypeCommande typeCommande; // Type de commande (ex: à emporter, livraison)
    private EtatCommande etatCommande; // État actuel de la commande
    public final double TAUX_TPS = 0.05; // Taux de la taxe de vente provinciale (TPS)
    public final double TAUX_TVQ = 0.09975; // Taux de la taxe de vente du Québec (TVQ)

    // Constructeur
    public Commande(TypeCommande typeCommande) {
        NO_COMMANDE = genererNoCommande(); // Générer un numéro de commande unique
        listeItems = new ArrayList<>(); // Initialiser la liste des articles
        this.typeCommande = typeCommande; // Définir le type de commande
        etatCommande = EtatCommande.EN_PREPARATION; // Définir l'état initial de la commande
    }

    // Méthode privée pour générer un numéro de commande unique basé sur la date et l'heure actuelles
    private String genererNoCommande() {
        String noCommande = "# ";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        noCommande += now.format(format);
        return noCommande;
    }

    // Méthodes pour accéder aux attributs de la commande
    public String getNO_COMMANDE() {
        return NO_COMMANDE;
    }

    public ArrayList<Item> getListeItems() {
        return listeItems;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }

    // Méthodes pour ajouter et retirer des articles de la commande
    public void ajouterItem(Item item) {
        listeItems.add(item);
        listeItems.sort(Comparator.naturalOrder()); // Tri des articles par ordre naturel
    }

    public void retirerItem(Item item) {
        listeItems.remove(item);
    }

    // Méthodes pour calculer le sous-total, les taxes et le total de la commande
    public double sousTotal() {
        double sousTotal = 0.0;
        for (Item item : listeItems) {
            sousTotal += item.getPrix().doubleValue();
        }
        return sousTotal;
    }

    public double tps(Double sousTotal) {
        return sousTotal * TAUX_TPS;
    }

    public double tvq(Double sousTotal) {
        return sousTotal * TAUX_TVQ;
    }

    public double total() {
        return sousTotal() + tps(sousTotal()) + tvq(sousTotal());
    }
}
