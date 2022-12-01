package modele.deplacements;

import modele.plateau.*;

public class IA extends RealisateurDeDeplacement {
    private Direction directionCourante = Direction.gauche;
    protected boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            System.out.println(directionCourante);
            Entite edirection = e.regarderDansLaDirection(directionCourante);
            System.out.println(directionCourante);
            if (e.estSur != null) {
                if (e.estSur.peutPermettreDeMonterDescendre() && Math.random() >= 0.20) {
                    if (Math.random() < 0.5){
                        directionCourante = Direction.haut;
                    }else {
                        directionCourante = Direction.bas;
                    }
                }else {
                    if (Math.random() > 0.5){
                        directionCourante = Direction.gauche;
                    }else{
                        directionCourante = Direction.droite;
                    }
                }
            }

            if (edirection == null || edirection.peutEtreTraverse() || edirection.peutMourir()) {
                //System.out.println(edirection);
                if (edirection instanceof Heros) ((EntiteVivante) edirection).vivant = false;
                if (e.avancerDirectionChoisie(directionCourante))
                    ret = true;
            } else {
                if (directionCourante == Direction.droite)
                    directionCourante = Direction.gauche;
                else if (directionCourante == Direction.gauche)
                    directionCourante = Direction.droite;
            }
        }

        return ret; } // TODO
}
