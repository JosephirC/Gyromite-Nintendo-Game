package modele.deplacements;

import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.EntiteVivante;

import java.util.ArrayList;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {

    private Direction directionCourante;

    private static ControleColonne colonneR;

    private static ControleColonne colonneB;

    //private boolean regardeHaut = true;

    Direction regarde = Direction.bas;



    public void resetDirection() {
        directionCourante = null;
    }

    public static ControleColonne getInstanceRouge() {
        if (colonneR == null) {
            colonneR = new ControleColonne();
        }
        return colonneR;
    }

    public static ControleColonne getInstanceBleu() {
        if (colonneB == null) {
            colonneB = new ControleColonne();
        }
        return colonneB;
    }

    public static ControleColonne resetb() {
        colonneB = new ControleColonne();
        return colonneB;
    }
    public static ControleColonne resetr() {
        colonneR = new ControleColonne();
        return colonneR;
    }


    /*public void setDirectionCourante(Direction _directionCourante) {

        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (changeDir == false)
                directionCourante = Direction.haut;
            else directionCourante =Direction.bas;
        }

    }*/


    public void setDirectionCouranteb(Direction _dir) {
        directionCourante = _dir;
    }

    public void setDirectionCouranter(Direction _dir) {
        directionCourante = _dir;
    }

    public void setDirectionCourante(Direction _dir) {
        directionCourante = _dir;
    }


    public void setDirectionCouranteAA(Direction _directionCourante) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != regarde)
                directionCourante = _directionCourante;
        }
    }

    public boolean realiserDeplacement() {
        boolean ret = false;

        /* for (EntiteDynamique e : lstEntitesDynamiques){
            if (directionCourante != null){
                if(e.avancerDirectionChoisie(directionCourante)) 0{
                    //System.out.println("Dir cour de la colonne " + e + directionCourante);
                    ret = true; }
            }
        } */
            /*for (int i = 0; i < lstEntitesDynamiques.size(); i++) {
                EntiteDynamique e = lstEntitesDynamiques.get(i);
            //if (e.peutSeDeplacer()) */
                if (directionCourante != null) {
                    switch (directionCourante) {
                        case haut:
                                for (EntiteDynamique e: lstEntitesDynamiques) {
                                    Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                                    if (ehaut != null) {
                                        if (ehaut.peutEtreEcrase()) {
                                            if (ehaut.peutMourir()) {
                                                Entite ehauthaut = ((EntiteDynamique) ehaut).regarderDansLaDirection(Direction.haut);
                                                if (ehauthaut != null)
                                                    ((EntiteVivante) ehaut).vivant = false;
                                                else {
                                                    ((EntiteVivante) ehaut).avancerDirectionChoisie(Direction.haut);
                                                    if (e.avancerDirectionChoisie(Direction.haut))
                                                        ret = true;
                                                }
                                            }
                                        }
                                    } else {

                                        if (e.avancerDirectionChoisie(Direction.haut))
                                            ret = true;
                                    }
                                }
                                break;

                        case bas:
                            // EntiteDynamique[] l = (EntiteDynamique[]) lstEntitesDynamiques.toArray();
                            EntiteDynamique[] l = new EntiteDynamique[lstEntitesDynamiques.size()];
                            lstEntitesDynamiques.toArray(l);
                            for (int i = l.length - 1; i >= 0; --i) {
                                EntiteDynamique e = l[i];
                                Entite ebas = e.regarderDansLaDirection(Direction.bas);
                                if (ebas != null) {
                                    if (ebas.peutEtreEcrase()) {
                                        if (ebas.peutMourir()) {
                                            Entite ehauthaut = ((EntiteDynamique) ebas).regarderDansLaDirection(Direction.haut);
                                            if (ehauthaut != null) {
                                                ((EntiteVivante) ebas).vivant = false;
                                            } else {
                                                ((EntiteVivante) ebas).avancerDirectionChoisie(Direction.haut);
                                                if (e.avancerDirectionChoisie(Direction.haut))
                                                    ret = true;

                                            }
                                        }
                                    }


                                } else {

                                    if (e.avancerDirectionChoisie(Direction.bas))
                                        ret = true;
                                }
                            }
                                break;
                            //}

                    }
                    // ((Colonne) e).move();
                } /*else {
                    if (((Colonne) e).get_move() ==0){
                        ((Colonne) e).init_move();
                        this.resetDirection();
                    }
                }*/
        return ret;
    }






}

