package modele.plateau;

public abstract class EntiteRamassable extends EntiteDynamique{

    public EntiteRamassable(Jeu _jeu) { super(_jeu);}

    public boolean peutServirDeSupport() { return false; }
    public boolean peutEtreRamasse(){return true;}

}
