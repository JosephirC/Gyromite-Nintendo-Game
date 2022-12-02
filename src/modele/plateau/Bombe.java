package modele.plateau;

public class Bombe extends EntiteStatique{

    public Bombe(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreRamasse(){return true;}
    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean peutSeDeplacer() { return false; }
    public boolean peutEtreTraverse(){ return false; }




}
