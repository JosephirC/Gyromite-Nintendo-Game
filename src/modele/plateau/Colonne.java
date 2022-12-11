package modele.plateau;

import modele.deplacements.Direction;

import java.util.ArrayList;

public class Colonne extends EntiteDynamique {
    public Colonne(Jeu _jeu, int hm, Direction dir) {
        super(_jeu);
        posM = hm;
        init_move();
        pousse_dir = dir;
    }

    public Colonne(Jeu _jeu,int hm,int _pos,Direction dir,int n) {
        super(_jeu);
        posM = hm;
        pos = _pos;
        init_move();
        pousse_dir = null;
        pousse_dir_tamp = Direction.bas;
        nb = n;
    }



    public int pos;

    public int posM;

    public int nb;

    private int nbmove;


    public void move(){
        nbmove--;
    }

    public int get_move(){
        return nbmove ;
    }

    public void init_move(){
        nbmove = 2;
    }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }

    @Override
    public boolean peutEtreTraverse() {
        return false;
    }

    public boolean peutPermettreDeMonterDescendre() { return false; }

    /*@Override
    public boolean peutSeDeplacer() {
        return false;
    }*/

    @Override
    public boolean peutEtreRamasse() {
        return false;
    }

    @Override
    public boolean peutRamasser() {
        return false;
    }

    /*@Override
    public boolean peutDistraire() {
        return false;
    }*/

    ;
    public boolean peutetreramasser() { return false; };
}
