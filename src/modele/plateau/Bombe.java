package modele.plateau;

public class Bombe extends EntiteRamassable{
    public Bombe(Jeu _jeu) {
        super(_jeu);
    }

    @Override
    public boolean peutRamasser() {
        return false;
    }
    //public boolean peutDistraire () { return false;}
}
