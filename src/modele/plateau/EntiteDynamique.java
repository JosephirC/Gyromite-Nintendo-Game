package modele.plateau;

import modele.deplacements.Direction;
import modele.deplacements.Ramassage;

/**
 * Entités amenées à bouger (colonnes, ennemis)
 */
public abstract class EntiteDynamique extends Entite {

    public EntiteDynamique(Jeu _jeu) { super(_jeu); }

    private Direction colonneDir;
    private Direction colonneDirTempo;

    public boolean avancerDirectionChoisie(Direction d) {
        return jeu.deplacerEntite(this, d);
    }

//    public boolean ramasserDirectionChoisie(Direction d, Ramassage r){
//        return jeu.ramassageEntite(this, d, r);
//    }

    public Entite regarderDansLaDirection(Direction d) {return jeu.regarderDansLaDirection(this, d);}

    public Direction setColonneDir(Direction dir) { return colonneDir = dir; }
    public Direction getColonneDir() { return colonneDir; }
    public Direction setColonneDirTempo(Direction dir) { return colonneDirTempo = dir; }
    public Direction getColonneDirTempo() { return colonneDirTempo; }

}