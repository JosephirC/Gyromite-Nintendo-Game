/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {
    public Heros(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };

   /* @Override
    public void collisionAvec(Entite e){
        if(e instanceof Corde){
            System.out.println("C'est une corde");
        }
    }

    public Entite vs_corde(Entite e) {

    }


    void est_contre(entite bot){
        jouer = mort
    }

    void est_contre(Entite corde){
        joueur.y++


    boucle:
        e.est_contre(instanceof Pcible){ // Pcible == corde

        }
    */



}
