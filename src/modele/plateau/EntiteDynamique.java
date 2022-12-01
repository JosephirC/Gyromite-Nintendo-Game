package modele.plateau;

import modele.deplacements.Direction;

/**
 * Entités amenées à bouger (colonnes, ennemis)
 */
public abstract class EntiteDynamique extends Entite {

    public EntiteDynamique(Jeu _jeu) { super(_jeu); }

    public boolean avancerDirectionChoisie(Direction d) {
        return jeu.deplacerEntite(this, d);
    }
    public Entite regarderDansLaDirection(Direction d) {return jeu.regarderDansLaDirection(this, d);}


    
    //public abstract void collisionAvec(Entite e);
    //public abstract void est_face_a(Entite e);
}