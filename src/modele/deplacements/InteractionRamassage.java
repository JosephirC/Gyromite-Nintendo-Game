package modele.deplacements;

import modele.plateau.EntiteDynamique;

public class InteractionRamassage extends RealisateurDeDeplacement{

    private Direction directionCourante;
    Ramassage ramassageObjet;

    private static InteractionRamassage ir;

    public static InteractionRamassage getInstance(){
        if(ir == null){
            ir = new InteractionRamassage();
        }
        return ir;
    }

    public void setRamassage(Ramassage _r, Direction _d){
        ramassageObjet = _r;
        directionCourante = _d;
    }

    // Je veux controler les interactions entre le jouer et les entites ramassables (radis)
    protected boolean realiserDeplacement() {
        boolean ret = false;
        System.out.println("i am called");

        /*for (int i = 0; i < lstEntitesDynamiques.size(); i++) {
            EntiteDynamique e = lstEntitesDynamiques.get(i);
            System.out.println("Je suis un " + e);
            if (ramassageObjet != null && ramassageObjet == Ramassage.espace){
                if (e.ramasserDirectionChoisie(directionCourante, ramassageObjet) || e.peutEtreRamasse())
                    ret = true;
            }
        }*/

        /*for (EntiteDynamique e : lstEntitesDynamiques) {
            if (ramassageObjet != null && ramassageObjet == Ramassage.espace){

                if (e.ramasserDirectionChoisie(directionCourante, ramassageObjet) ) //|| e.peutEtreRamasse()
                    ret = true;
            }
        }
        */
        return ret;
    }

    public void resetRamassage() {
        ramassageObjet = null;
    }



    //ramasserDirectionChoisie


    /*for (EntiteDynamique e : lstEntitesDynamiques) {
        if (interactionCourante != null && interactionCourante == Interaction.Entree || interactionCourante == Interaction.e){
            if (e.interactionObjetCourant(interactionCourante, directionCourante))
                ret = true;
        }
    }*/


}
