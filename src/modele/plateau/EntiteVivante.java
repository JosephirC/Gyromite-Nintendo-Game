package modele.plateau;
/* pour vie ou mort des perso ptre */
public abstract class EntiteVivante extends EntiteDynamique{

    private boolean vivant;

    public boolean peutEtreEcrase() { return true; }
    public boolean peutEtreTraverse(){ return false; }

    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public EntiteVivante(Jeu _jeu) { super(_jeu);estSur = null;vivant = true; }

    public boolean peutMourir() { return  true; }

    public boolean setVivant(boolean etat){
        vivant = etat;
        return vivant;
    }

    public boolean getVivant(){
        return vivant;
    }
}
