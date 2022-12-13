/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;


public abstract class Entite {
    protected Jeu jeu;

    public Entite estSur;

    public Entite(Jeu _jeu) {
        jeu = _jeu;
    }
    /**
     * @fn abstract boolean peutEtreEcrase()
     */
    public abstract boolean peutEtreEcrase(); // l'entité peut être écrasée (par exemple par une colonne ...)
    /**
     * @fn abstract boolean peutServirDeSupport()
     */
    public abstract boolean peutServirDeSupport(); // permet de stopper la gravité, prendre appui pour sauter
    /**
     * @fn abstract boolean peutEtreTraverse()
     */
    public abstract boolean peutEtreTraverse(); // permet de traverser une entite
    /**
     * @fn abstract boolean peutPermettreDeMonterDescendre()
     */
    public abstract boolean peutPermettreDeMonterDescendre(); // si utilisation de corde (attention, l'environnement ne peut pour l'instant sotker qu'une entité par case (si corde : 2 nécessaires), améliorations à prévoir)
    //public abstract boolean peutSeDeplacer(); // permet de deplacer une colonne
    /**
     * @fn peutMourir()
     * @return false
     */
    public boolean peutMourir() { return false; };
    /**
     * @fn peutEtreRamasse()
     */
    public abstract boolean peutEtreRamasse();
     /**
     * @fn peutRamasser()
     */
    public abstract boolean peutRamasser();
    //public abstract boolean peutDistraire();

}
