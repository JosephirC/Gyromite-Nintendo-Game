package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {

    private Direction directionCourante;

    private static ControleColonne c3d;
    protected boolean realiserDeplacement() {
        for(EntiteDynamique ed : lstEntitesDynamiques){
            //System.out.println("ED IS " + ed);
        }

        return false;
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

        /* ex
        public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e instanceof Colonne)
                if (directionCourante != null && ((Colonne) e).get_move() != 0) {
                    System.out.println("coucou normalement je bouge");
                    switch (directionCourante) {
                        case haut:
                            Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                            if (ehaut == null) {
                                if (e.avancerDirectionChoisie(Direction.haut))
                                    ret = true;
                                System.out.println("coucou je monte pas enffet lol");
                            } else {
                                if (ehaut.peutEtreEcrase()) {
                                    if (ehaut instanceof EntiteVivante) {
                                        Entite ehauthaut = ((EntiteDynamique) ehaut).regarderDansLaDirection(Direction.haut);
                                        if (ehauthaut != null) {
                                            ((EntiteVivante) ehaut).vivant = false;
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
                            if (ebas == null || ebas.peutEtreEcrase()) {
                                if (e.avancerDirectionChoisie(Direction.bas))
                                    ret = true;
                            } else {
                                if (ebas.peutEtreEcrase()) {
                                    if (ebas instanceof EntiteVivante) {
                                        Entite ebasbas = ((EntiteDynamique) ebas).regarderDansLaDirection(Direction.bas);
                                        if (ebasbas != null)
                                            ((EntiteVivante) ebas).vivant = false;

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
         */
}
