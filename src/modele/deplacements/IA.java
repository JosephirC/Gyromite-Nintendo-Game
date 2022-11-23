package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

public class IA extends RealisateurDeDeplacement {
    private Direction directionCourante = Direction.gauche;
    protected boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite egauche = e.regarderDansLaDirection(directionCourante);
            if ( egauche == null) {
                if (e.avancerDirectionChoisie(directionCourante))
                    ret = true;
            }else{
                if (directionCourante == Direction.gauche)
                    directionCourante = Direction.droite;
                else
                    directionCourante = Direction.gauche;
            }
        }

        return ret; } // TODO
}
