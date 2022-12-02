package modele.deplacements;

import modele.plateau.EntiteDynamique;

public class InteractionRamassage extends RealisateurDeDeplacement{

    private Direction directionCourante;
    Ramassage ramassageCourant;


    // Je voulais controler les interactions entre le jouer et les entites ramassables
    @Override
    protected boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (ramassageCourant != null && ramassageCourant == Ramassage.espace){
                if (e.avancerDirectionChoisie(directionCourante) || e.peutEtreRamasse())
                    ret = true;
            }
        }
        return ret;
    }
}
