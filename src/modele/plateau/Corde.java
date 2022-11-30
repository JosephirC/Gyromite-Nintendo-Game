package modele.plateau;

public class Corde extends EntiteStatique {

    public Corde(Jeu _jeu){
        super(_jeu);
    }

    @Override
    public boolean peutEtreTraverse(){ return true; }
}
