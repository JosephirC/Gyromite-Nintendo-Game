package modele.deplacements;

import modele.plateau.*;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le clavier) à un ensemble d'entités dynamiques
 */
public class Controle4Directions extends RealisateurDeDeplacement {
    private static Direction directionCourante;
    // Design pattern singleton
    private static Controle4Directions c3d;

    public static Controle4Directions reset() {
        c3d = new Controle4Directions();
        return c3d;
    }
    public static Controle4Directions getInstance() {
        if (c3d == null) {
            c3d = new Controle4Directions();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }

    public static Direction getDirection(){
        return directionCourante;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
                if (directionCourante != null)
                    switch (directionCourante) {
                        case gauche:
                            Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
                            if ((eGauche == null) || (eGauche.peutEtreTraverse()) || (eGauche.peutEtreRamasse())) {
                                if (e.avancerDirectionChoisie(Direction.gauche))
                                    if (e instanceof Bot) ((EntiteVivante) e).vivant = false;
                                        ret = true;
                            }
                            break;
                        case droite:
                            Entite eDroite = e.regarderDansLaDirection(Direction.droite);
                            if ((eDroite == null) || (eDroite.peutEtreTraverse()) || (eDroite.peutEtreRamasse())) {
                                if (e.avancerDirectionChoisie(Direction.droite))
                                    if (e instanceof Bot) ((EntiteVivante) e).vivant = false;
                                        ret = true;
                            }
                            break;
                        case bas:
                            Entite eBas = e.regarderDansLaDirection(Direction.bas);
                            if (eBas != null && (eBas.peutServirDeSupport() || eBas.peutEtreTraverse())) {
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    if (e instanceof Bot) ((EntiteVivante) e).vivant = false;
                                        ret = true;
                            }
                            break;

                        case haut:
                            // on ne peut pas sauter sans prendre appui
                            // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)
                            Entite eHaut = e.regarderDansLaDirection(Direction.bas);
                            if (eHaut != null && eHaut.peutServirDeSupport() || eHaut != null && eHaut.peutEtreTraverse()) {
                                if (e.avancerDirectionChoisie(Direction.haut))
                                    if (e instanceof Bot) ((EntiteVivante) e).vivant = false;
                                        ret = true;
                            }
                            break;
                    }

        }

        return ret;

    }

    public void resetDirection() {
        directionCourante = null;
    }
}
