/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

import java.awt.*;


/**
 * Héros du jeu
 */
public class Heros extends EntiteVivante {
    public Heros(Jeu _jeu) {
        super(_jeu);
    }
    public boolean peutEtreRamasse() { return false; }
    public  boolean peutRamasser(){ return true; }
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }

    public boolean peutEtreTraverse(){ return false; }

    @Override
    public boolean peutDistraire() { return false; }


}
