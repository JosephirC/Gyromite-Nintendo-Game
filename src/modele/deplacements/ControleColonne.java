package modele.deplacements;

import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.EntiteVivante;
import org.w3c.dom.ls.LSOutput;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le clavier) à un ensemble d'entités dynamiques
 */
public class ControleColonne extends RealisateurDeDeplacement {
    private Direction directionCourante;
    // Design pattern singleton
    private static ControleColonne c3d;
    private static ControleColonne colonneR;
    private static ControleColonne colonneB;
    public boolean in = true;


    //public Direction tempPLZ = Direction.haut;

    public void resetDirection() {
        directionCourante = null;
    }



    public static ControleColonne reset() {
        c3d = new ControleColonne();
        return c3d;
    }

    public static ControleColonne resetRouge() {
        colonneR = new ControleColonne();
        return colonneR;
    }

    public static ControleColonne resetBleu() {
        colonneB = new ControleColonne();
        return colonneB;
    }


    public static ControleColonne getInstance() {
        if (c3d == null) {
            c3d = new ControleColonne();
        }
        return c3d;
    }

    public static ControleColonne getInstanceR() {
        if (colonneR == null) {
            colonneR = new ControleColonne();
        }
        return colonneR;
    }

    public static ControleColonne getInstanceB() {
        if (colonneB == null) {
            colonneB = new ControleColonne();
        }
        return colonneB;
    }


    public void setDirectionCourante(Direction _directionCourante,int nb) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != e.pousse_dir_tamp)
                if (e instanceof Colonne)
                    if (((Colonne) e).nb == nb) {
                        //System.out.println("coucou n est egal a :"+  nb);
                        //System.out.println(((Colonne) e).get_move());
                        e.pousse_dir = _directionCourante;
                        //tempPLZ = _directionCourante;
                    }else {
                        e.pousse_dir = null;
                    }
        }
    }

    public void setDirectionCouranteR(Direction _directionCourante,int nb) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != e.pousse_dir_tamp)
                if (e instanceof Colonne)
                    if (((Colonne) e).nb == nb) {
                        //System.out.println("coucou n est egal a :"+  nb);
                        //System.out.println(((Colonne) e).get_move());
                        e.pousse_dir = _directionCourante;
                        //tempPLZ = _directionCourante;
                    }else {
                        e.pousse_dir = null;
                    }
        }
    }
    public void setDirectionCouranteB(Direction _directionCourante,int nb) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != e.pousse_dir_tamp)
                if (e instanceof Colonne)
                    if (((Colonne) e).nb == nb) {
                        //System.out.println("coucou n est egal a :"+  nb);
                        //System.out.println(((Colonne) e).get_move());
                        e.pousse_dir = _directionCourante;
                        //tempPLZ = _directionCourante;
                    }else {
                        e.pousse_dir = null;
                    }
        }
    }




    public boolean realiserDeplacement() {
        boolean ret = false;
        EntiteDynamique[] gHaut = new EntiteDynamique[lstEntitesDynamiques.size()];
        lstEntitesDynamiques.toArray(gHaut);

        /*EntiteDynamique [] temp = new EntiteDynamique[lstEntitesDynamiques.size()];
        EntiteDynamique[] clone = gHaut;
        for(int a = 0; a< clone.length/2; a++){
            temp[a] = clone[a];
            clone[a] = clone[clone.length - a - 1];
            clone[clone.length - a - 1] = temp[a];
        }*/
        for (int i =0; i<gHaut.length; i++) {
            EntiteDynamique e = gHaut[i];
            if (e instanceof Colonne)
                if (e.pousse_dir != null && ((Colonne) e).get_move() != 0) {
                    switch (e.pousse_dir) {
                        case haut:
                            Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                            if (ehaut == null) {
                                if (e.avancerDirectionChoisie(Direction.haut)) {
                                    System.out.println("e is " +e);
                                    ret = true;
                                    e.pousse_dir_tamp = Direction.haut;
                                }
                            } else {
                                if (ehaut.peutEtreEcrase()) {
                                    if (ehaut instanceof EntiteVivante) {
                                        Entite ehauthaut = ((EntiteDynamique) ehaut).regarderDansLaDirection(Direction.haut);
                                        if (ehauthaut != null) {
                                            ((EntiteVivante) ehaut).setVivant(false);
                                        } else {
                                            ((EntiteVivante) ehaut).avancerDirectionChoisie(Direction.haut);
                                            if (e.avancerDirectionChoisie(Direction.haut)) {
                                                ret = true;
                                                e.pousse_dir_tamp = Direction.bas;
                                            }
                                        }
                                    }
                                }
                            }
                            break;

                        case bas :
                            EntiteDynamique [] temp = new EntiteDynamique[lstEntitesDynamiques.size()];
                            lstEntitesDynamiques.toArray(temp);
                            EntiteDynamique[] clone = gHaut;

                            if(in == true){
                                for(int a = 0; a< clone.length/2; a++){
                                    //System.out.println(clone.length/2);
                                    temp[a] = clone[a];
                                    clone[a] = clone[clone.length - a - 1];
                                    clone[clone.length - a - 1] = temp[a];
                                    //System.out.println(" i am " + clone[a]);
                                }
                                in = false;
                            }
                                    e = clone[i];

                            System.out.println("did i get reversed ? " + clone[0]);
                            System.out.println("did i get reversed ? " + clone[1]);
                            System.out.println("did i get reversed ? " + clone[2]);
                                    if (e.pousse_dir != null && ((Colonne) e).get_move() != 0) {
                                        Entite ebas = e.regarderDansLaDirection(Direction.bas);
                                        System.out.println("je suis " + e);
                                        if (ebas == null || ebas.peutEtreEcrase()) {
                                            if (e.avancerDirectionChoisie(Direction.bas)) {
                                                ret = true;
                                                e.pousse_dir_tamp = Direction.bas;
                                            }
                                        } else {
                                            if (ebas.peutEtreEcrase()) {
                                                if (ebas instanceof EntiteVivante) {
                                                    Entite ebasbas = ((EntiteDynamique) ebas).regarderDansLaDirection(Direction.bas);
                                                    if (ebasbas != null)
                                                        ((EntiteVivante) ebas).setVivant(false);
                                                }
                                            }
                                        }
                                    }
                                    if(i==2)
                                        in = true;
                                    break;
                    }
                    ((Colonne) e).move();
                } else if (((Colonne) e).get_move() == 0) {
                    ((Colonne)e).init_move();
                    e.pousse_dir = null;
                }
        }
        return ret;
    }
}
