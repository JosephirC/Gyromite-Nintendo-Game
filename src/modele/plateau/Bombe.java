package modele.plateau;

public class Bombe extends EntiteRamassable{
    public Bombe(Jeu _jeu) {
        super(_jeu);
    }
    public boolean peutDistraire () { return false;}
}
