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
