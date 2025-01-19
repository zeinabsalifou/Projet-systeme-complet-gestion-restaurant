package tp1.interfacedevoire;

// Définition de l'énumération EtatCommande
public enum EtatCommande {
    // Définition des différentes valeurs de l'énumération avec leurs descriptions
    SERVICE_COMPLETE ("Service complété"),
    PRETE_A_LIVRER ("Prête à être livrée"),
    EN_PREPARATION ("En préparation"),
    TRANSACTION_EN_COURS ("Transaction en cours");

    // Attribut pour stocker la description de l'état de la commande
    private String etatCommande;

    // Constructeur de l'énumération EtatCommande
    EtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    // Méthode pour obtenir la description de l'état de la commande
    public String getEtatCommandeEnum() {
        return etatCommande;
    }
}
