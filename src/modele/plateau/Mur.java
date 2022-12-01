package modele.plateau;

public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu, boolean b) {
        super(_jeu);
        brique = b;
    }

    public boolean brique;

}