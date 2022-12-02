package modele.plateau;

public abstract class EntiteRamassable extends EntiteDynamique{

    public EntiteRamassable(Jeu _jeu) { super(_jeu);}
    public boolean peutServirDeSupport() { return false; }
    public boolean peutEtreRamasse(){return true;}
    public boolean peutEtreEcrase() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
   public boolean peutSeDeplacer() { return false; }
   public boolean peutEtreTraverse(){ return true; }


}
