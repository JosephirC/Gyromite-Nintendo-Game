package modele.plateau;

public class Radis extends EntiteRamassable{
    public Radis(Jeu _jeu) {
        super(_jeu);
    }

    @Override
    public boolean peutRamasser() {
        return false;
    }
    //public boolean peutDistraire () { return true;}
}
