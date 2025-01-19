package tp1.interfacedevoire;

public enum ModeDePaiement {
    DEBIT ("débit"),           // Mode de paiement par débit
    CREDIT ("crédit"),         // Mode de paiement par crédit
    ARGENT ("argent"),         // Paiement en espèces
    CARTE_CADEAU ("carte cadeau"); // Mode de paiement par carte cadeau

    private String modeDePaiement; // Chaîne représentant le mode de paiement

    // Constructeur pour associer une chaîne à chaque mode de paiement
    ModeDePaiement(String modeDePaiement) {
        this.modeDePaiement= modeDePaiement;
    }

    // Méthode pour obtenir la représentation textuelle du mode de paiement
    public String getModeDePaiementEnum() {
        return modeDePaiement;
    }
}
