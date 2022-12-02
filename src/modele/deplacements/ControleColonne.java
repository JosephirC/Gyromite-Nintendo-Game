package modele.deplacements;

import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.EntiteVivante;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {

    private Direction directionCourante;

    private static ControleColonne c3d;

    public void resetDirection() {
        directionCourante = null;
    }

    public static ControleColonne getInstance() {
        if (c3d == null) {
            c3d = new ControleColonne();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }



    public boolean realiserDeplacement() {


        /*boolean ret = false;

        for (int i = 0; i < lstEntitesDynamiques.size(); i++) {
            EntiteDynamique e = lstEntitesDynamiques.get(i);
            if (directionCourante != null){
                if(e.avancerDirectionChoisie(directionCourante)) { ret = true; }
            }
        }
        return ret;*/




        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e.peutSeDeplacer())
                if (directionCourante != null /*&& ((Colonne) e).get_move() != 0*/) {
                    switch (directionCourante) {
                        case haut:
                            Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                            if (ehaut == null) {
                                //System.out.println("je suis ehaut null ");
                                if (e.avancerDirectionChoisie(Direction.haut))
                                    ret = true;
                            } else {
                                if (ehaut.peutEtreEcrase()) {
                                    //System.out.println("je suis ehaut peutEtreEcrase ");
                                    //System.out.println(ehaut.peutMourir());
                                    if (ehaut.peutMourir()) {
                                        Entite ehauthaut = ((EntiteDynamique) ehaut).regarderDansLaDirection(Direction.haut);
                                        if (ehauthaut != null) {

                                            ((EntiteVivante) ehaut).vivant = false;
                                            //System.out.println("je suis " + ehaut + "et je suis " + ((EntiteVivante) ehaut).vivant);
                                        } else {
                                            ((EntiteVivante) ehaut).avancerDirectionChoisie(Direction.haut);
                                            if (e.avancerDirectionChoisie(Direction.haut))
                                                ret = true;
                                        }
                                    }

                                }
                            }
                            break;
                        case bas:
                            Entite ebas = e.regarderDansLaDirection(Direction.bas);
                            if (ebas == null) {
                                //System.out.println("je suis null");
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    ret = true;
                            } else {
                                if (ebas.peutEtreEcrase()) {
                                    //System.out.println("je suis ehaut peutEtreEcrase ");
                                    //System.out.println(ebas.peutMourir());
                                    if (ebas.peutMourir()) {
                                        Entite ehauthaut = ((EntiteDynamique) ebas).regarderDansLaDirection(Direction.haut);
                                        if (ehauthaut != null) {

                                            ((EntiteVivante) ebas).vivant = false;
                                            //System.out.println("je suis " + ebas + "et je suis " + ((EntiteVivante) ebas).vivant);
                                        } else {
                                            ((EntiteVivante) ebas).avancerDirectionChoisie(Direction.haut);
                                            if (e.avancerDirectionChoisie(Direction.haut))
                                                ret = true;
                                        }
                                    }
                                }
                            }
                            break;
                    }
                    //((Colonne) e).move();
                } //else
                //if (((Colonne) e).get_move() ==0){
                    //((Colonne) e).init_move();
                    //this.resetDirection();
                //}
        }
        return ret;
    }


    public boolean realiserDeplacementTWO() {
        boolean ret = false;

        for (int i = 0; i < lstEntitesDynamiques.size(); i++) {
            EntiteDynamique e = lstEntitesDynamiques.get(i);
            if (directionCourante != null){
                if(e.avancerDirectionChoisie(directionCourante)) { ret = true; }
            }
        }
        return ret;
    }


}
/*
boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
                if (directionCourante != null)
                    switch (directionCourante) {
                        case gauche:
                            Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
                            if ((eGauche == null) || (eGauche.peutEtreTraverse())) {
                                if (e.avancerDirectionChoisie(Direction.gauche))
                                    ret = true;
                            }
                            break;
                        case droite:
                            Entite eDroite = e.regarderDansLaDirection(Direction.droite);
                            if ((eDroite == null) || (eDroite.peutEtreTraverse())) {
                                if (e.avancerDirectionChoisie(Direction.droite))
                                    ret = true;
                            }
                            break;
                        case bas:
                            Entite eBas = e.regarderDansLaDirection(Direction.bas);
                            if (eBas != null && (eBas.peutServirDeSupport() || eBas.peutEtreTraverse())) {
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    ret = true;
                            }
                            break;

                        case haut:
                            // on ne peut pas sauter sans prendre appui
                            // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)
                            Entite eHaut = e.regarderDansLaDirection(Direction.bas);
                            if (eHaut != null && eHaut.peutServirDeSupport() || eHaut != null && eHaut.peutEtreTraverse()) {
                                if (e.avancerDirectionChoisie(Direction.haut))
                                    ret = true;
                            }
                            break;
                    }

        }

        return ret;

    }

 */


