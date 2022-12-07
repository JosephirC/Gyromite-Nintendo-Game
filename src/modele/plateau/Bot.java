/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteEnnemi {
    private Random r = new Random();

    public Bot(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
    public boolean peutSeDeplacer() { return false; }
    public boolean peutEtreTraverse(){ return false; }
    public boolean peutEtreRamasse(){return false;}

    @Override
    public boolean peutDistraire() {
        return false;
    }

}
