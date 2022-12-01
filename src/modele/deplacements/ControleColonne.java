package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {

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
}
