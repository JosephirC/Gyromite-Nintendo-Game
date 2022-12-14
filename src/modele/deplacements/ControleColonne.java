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
    private static ControleColonne colonneR;
    private static ControleColonne colonneB;
    public boolean peutCloner = true;

    public void resetDirection() {
        directionCourante = null;
    }

    public static ControleColonne resetColRouge() {
        colonneR = new ControleColonne();
        return colonneR;
    }

    public static ControleColonne resetColBleu() {
        colonneB = new ControleColonne();
        return colonneB;
    }

    public static ControleColonne getInstanceRouge() {
        if (colonneR == null)
            colonneR = new ControleColonne();
        return colonneR;
    }

    public static ControleColonne getInstanceBleu() {
        if (colonneB == null)
            colonneB = new ControleColonne();
        return colonneB;
    }

    public void setDirectionCouranteR(Direction _directionCourante,int couleur) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != e.getColonneDirTempo())
                //if (e instanceof Colonne)
                    if (((Colonne) e).getCouleur() == couleur)
                        //e.pousse_dir = _directionCourante;
                        e.setColonneDir(_directionCourante);
                    else
                        //e.pousse_dir = null;
                        e.setColonneDir(null);
        }
    }
    public void setDirectionCouranteB(Direction _directionCourante,int couleur) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (_directionCourante != e.getColonneDirTempo())
                //if (e instanceof Colonne)
                    if (((Colonne) e).getCouleur() == couleur)
                        //e.pousse_dir = _directionCourante;
                        e.setColonneDir(_directionCourante);
                    else
                        //e.pousse_dir = null;
                        e.setColonneDir(null);
        }
    }


    public boolean realiserDeplacement() {
        boolean ret = false;
        EntiteDynamique[] gHaut = new EntiteDynamique[lstEntitesDynamiques.size()];
        lstEntitesDynamiques.toArray(gHaut);

        for (int i =0; i<gHaut.length; i++) {
            EntiteDynamique e = gHaut[i];
            //if (e instanceof Colonne)
                if (e.getColonneDir() != null && ((Colonne) e).getNbrDeplacement() != 0) {
                    switch (e.getColonneDir()) {
                        case haut:
                            Entite ehaut = e.regarderDansLaDirection(Direction.haut);
                            if (ehaut == null) {
                                if (e.avancerDirectionChoisie(Direction.haut)) {
                                    //System.out.println("e est : " + e);
                                    ret = true;
                                    e.setColonneDirTempo(Direction.haut);
                                }
                            } else {
                                if (ehaut.peutEtreEcrase()) {
                                    if (ehaut.peutMourir()) {
                                        Entite ehauthaut = ((EntiteDynamique) ehaut).regarderDansLaDirection(Direction.haut);
                                        if (ehauthaut != null) {
                                            ((EntiteVivante) ehaut).setVivant(false);
                                        } else {
                                            ((EntiteVivante) ehaut).avancerDirectionChoisie(Direction.haut);
                                            if (e.avancerDirectionChoisie(Direction.haut)) {
                                                ret = true;
                                                e.setColonneDirTempo(Direction.bas);
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

                            if(peutCloner == true){
                                for(int a = 0; a< clone.length/2; a++){
                                    //System.out.println(clone.length/2);
                                    temp[a] = clone[a];
                                    clone[a] = clone[clone.length - a - 1];
                                    clone[clone.length - a - 1] = temp[a];
                                    //System.out.println(" je suis " + clone[a]);
                                }
                                peutCloner = false;
                            }
                                    e = clone[i];

                            if (e.getColonneDir() != null && ((Colonne) e).getNbrDeplacement() != 0) {
                                Entite ebas = e.regarderDansLaDirection(Direction.bas);
                                //System.out.println("ebas is " + ebas);
                                //System.out.println("je suis " + e);
                                if (ebas == null || ebas.peutEtreEcrase()) {
                                    if (e.avancerDirectionChoisie(Direction.bas)) {
                                        ret = true;
                                        e.setColonneDirTempo(Direction.bas);
                                    }
                                } else {
                                    if (ebas.peutEtreEcrase()) {
                                        if (ebas.peutMourir()) {
                                            Entite ebasbas = ((EntiteDynamique) ebas).regarderDansLaDirection(Direction.bas);
                                            if (ebasbas != null){
                                                ((EntiteVivante) ebas).setVivant(false);
                                                //System.out.println("ebas is " + ebasbas);
                                            }


                                        }
                                    }
                                }
                            }
                            if(i==2)
                                peutCloner = true;
                            break;
                    }
                    ((Colonne) e).deplaceColonne();
                } else if (((Colonne) e).getNbrDeplacement() == 0) {
                    ((Colonne)e).nbrDeplacementInit();
                    e.setColonneDir(null);
                }
        }
        return ret;
    }
}
