package modele.plateau;

import modele.deplacements.Direction;

import java.util.ArrayList;

public class Colonne extends EntiteDynamique {
    public Colonne(Jeu _jeu, int hm, Direction dir) {
        super(_jeu);
        posM = hm;
        nbrDeplacementInit();
        setColonneDir(dir);
    }

    public Colonne(Jeu _jeu,int _colonneMilieu,int _composanteColonne,int _couleur) {
        super(_jeu);
        posM = _colonneMilieu;
        colonneMilieu = _colonneMilieu;
        pos = _composanteColonne;
        composanteColonne = _composanteColonne;
        nbrDeplacementInit();
        setColonneDir(null);
        setColonneDirTempo(Direction.bas);
        couleur = _couleur;
    }

    private int colonneMilieu;
    public int pos;
    private int composanteColonne;
    public int posM;
    private int couleur;
    private int nbrDeplacement;

    public int setColonneMilieu(int colM) { return  colonneMilieu = colM; }
    public int getColonneMilieu() { return  colonneMilieu; }
    public int setComposanteColonne(int compCol) { return  composanteColonne = compCol; }
    public int getComposanteColonne() { return  composanteColonne; }


    public int setCouleur(int c) { return couleur = c; }
    public int getCouleur() { return couleur; }

    public void deplaceColonne() { nbrDeplacement--; }
    public int getNbrDeplacement() { return nbrDeplacement; }
    public void nbrDeplacementInit() {nbrDeplacement = 2; }


    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }

    @Override
    public boolean peutEtreTraverse() {
        return false;
    }

    public boolean peutPermettreDeMonterDescendre() { return false; }
    @Override
    public boolean peutEtreRamasse() { return false; }

    @Override
    public boolean peutRamasser() {
        return false;
    }
    public boolean peutetreramasser() { return false; };

    @Override
    public boolean peutDistraire() { return false; }
}
