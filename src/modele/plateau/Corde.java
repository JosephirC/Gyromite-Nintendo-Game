package modele.plateau;

public class Corde extends EntiteStatique {

    public Corde(Jeu _jeu){
        super(_jeu);
    }

    public boolean peutEtreTraverse(){ return true; }
    public boolean peutSeDeplacer() { return false; }

    public boolean peutPermettreDeMonterDescendre() { return true; };
    public boolean peutEtreRamasse() { return false; }

    @Override
    public boolean peutDistraire() {
        return false;
    }
}
