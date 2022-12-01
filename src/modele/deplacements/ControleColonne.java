package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {
    protected boolean realiserDeplacement() {
        for(EntiteDynamique ed : lstEntitesDynamiques){
            System.out.println("ED IS " + ed);







        }
        return false;
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
