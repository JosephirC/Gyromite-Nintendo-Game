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
    public Point alapos;
    public boolean peutSeDeplacer() { return false; }
    public boolean peutEtreRamasse() { return false; }

    public  boolean peutRamasser(){ return true; }

    /*@Override
    public boolean peutDistraire() {
        return false;
    }
*/
   /* public void est_face_a(modele.plateau.Bot bot){
        System.out.println("Mort");
    }

    public void est_face_a(modele.plateau.Corde corde){
        System.out.println("Monte");
    }

    public void est_face_a(){
        System.out.println("Monte");
    }*/
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
