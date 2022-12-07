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

    private static ControleColonne colonneR;

    private static ControleColonne colonneB;

    //private boolean regardeHaut = true;

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



    public boolean realiserDeplacement() {
        boolean ret = false;

        /*for (EntiteDynamique e : lstEntitesDynamiques){
            if (directionCourante != null){
                if(e.avancerDirectionChoisie(directionCourante)) {
                    //System.out.println("Dir cour de la colonne " + e + directionCourante);
                    ret = true; }
            }
        }*/

        for (EntiteDynamique e : lstEntitesDynamiques) {
            //if (e.peutSeDeplacer())
                if (directionCourante != null && ((Colonne) e).get_move() != 0 ) {
                    //System.out.println("Je suis la col " + e);
                    switch (directionCourante) {
                        case haut:

                                Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                                if (ehaut == null /*&& regardeHaut == true*/) {
                                    //System.out.println("je suis ehaut null ");
                                    if (e.avancerDirectionChoisie(Direction.haut))
                                        //regardeHaut = false;
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
                                                    //regardeHaut = false;
                                                    ret = true;
                                            }
                                        }
                                    }
                                }
                                break;

                        case bas:
                            Entite ebas = e.regarderDansLaDirection(Direction.bas);
                            if (ebas == null /*&& regardeHaut == false*/) {
                                //System.out.println("je suis null");
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    //regardeHaut = true;
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
                                                //regardeHaut = true;
                                                ret = true;
                                        }
                                    }
                                }
                            }
                            break;
                    }
                    ((Colonne) e).move();
                } else
                if (((Colonne) e).get_move() ==0){
                    ((Colonne) e).init_move();
                    this.resetDirection();
                }
        }
        return ret;
    }

}

