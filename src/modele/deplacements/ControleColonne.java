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

    public void setDirectionCourante(Direction _directionCourante) {
        directionCourante = _directionCourante;
    }



    public boolean realiserDeplacement() {
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

}

