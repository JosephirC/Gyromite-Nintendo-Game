package modele.plateau;

public class Colonne extends EntiteDynamique {
    public Colonne(Jeu _jeu) { super(_jeu); }

    public Colonne(Jeu _jeu,int hm,int _pos, int c) { super(_jeu);posM = hm;pos = _pos;init_move();couleur = c;}
    public int pos;
    public int posM;

    public int couleur; // Choisi int pour possible ajout de plusieurs couleurs
    private int nbmove;

    public int get_move(){
        return nbmove ;
    }
    public void move(){
        nbmove--;
    }
    public void init_move(){
        nbmove = 6;
    }
    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
    public boolean peutSeDeplacer() { return true; }
    public boolean peutEtreTraverse(){ return false; }
    public boolean peutEtreRamasse(){return false;}
}
