package modele.plateau;

public class Colonne extends EntiteDynamique {
    public Colonne(Jeu _jeu) { super(_jeu); }

    public Colonne(Jeu _jeu,int hm,int _pos) { super(_jeu);posM = hm;pos = _pos;init_move();}
    public int pos;
    public int posM;

    private int nbmove;

    public void init_move(){
        nbmove = 6;
    }
    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
    public boolean peutSeDeplacer() { return true; }
}
