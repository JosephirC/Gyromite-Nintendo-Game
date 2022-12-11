package modele.plateau;

public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu, boolean b) {
        super(_jeu);
        brique = b;
    }

    public boolean brique;


    //public boolean peutPermettreDeMonterDescendre() { return false; }
    //public boolean peutSeDeplacer() { return false; }
    public boolean peutEtreTraverse(){ return false; }
    public boolean peutEtreRamasse(){return false;}

    @Override
    public boolean peutRamasser() {
        return false;
    }

    /*@Override
    public boolean peutDistraire() {
        return false;
    }
*/
}