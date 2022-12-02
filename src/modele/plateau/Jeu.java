/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.*;
import modele.deplacements.ControleColonne;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    public int lvl;

    private int score = 0;
    private int vie = 3;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;

    private Bot smick;

    private Corde corde;

    private Colonne colonne;

    private Bombe bombe;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu(int level) {
        lvl = level;
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Heros getHector() {
        return hector;
    }

    public Bot getSmick() {
        return smick;
    }

    public int getScore(){
        return score;
    }

    private void initialisationDesEntites() {
        //addEntite(bombe, 5, 4);

        try{
            Gravite g = new Gravite();
            IA ia = new IA();
            File f = new File("src/levels/level_"+ lvl + ".txt");
            FileInputStream fIS = new FileInputStream(f);
            int r = 0;
            int x = 0;
            int y = 0;
            while((r = fIS.read())!=-1)
            {
                switch ((char)r){
                    case 'p':
                        addEntite(new Bombe(this), x, y);
                        x++;
                        break;
                    case 'b':
                        addEntite(new Mur(this, true), x, y);
                        x++;
                        break;
                    case 'm':
                        addEntite(new Mur(this, false), x, y);
                        x++;
                        break;
                    case 'c' :
                        addEntite(new Corde(this), x, y);
                        x++;
                        break;
                    case '_':
                        x++;
                        break;
                    case '/':
                        y++;
                        x = 0;
                        break;
                    case 'h':
                        hector = new Heros(this);
                        addEntite(hector, x, y);
                        g.addEntiteDynamique(hector);
                        Controle4Directions.getInstance().addEntiteDynamique(hector);
                        ordonnanceur.add(Controle4Directions.getInstance());
                        x++;
                        break;
                    case 'i' :
                        smick = new Bot(this);
                        addEntite(smick, x, y);
                        g.addEntiteDynamique(smick);
                        ia.addEntiteDynamique(smick);
                        x++;
                        break;
                    case 'l' :
                        initialisationdunecolone(x,y);
                        x++;
                        break;
                }

            }
            ordonnanceur.add(g);
            ordonnanceur.add(ia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*hector = new Heros(this);
                addEntite(hector, 2, 1);

                Gravite g = new Gravite();
                g.addEntiteDynamique(hector);

                smick = new Bot(this);
                addEntite(smick, 6, 8);

                IA ia = new IA();
                g.addEntiteDynamique(smick);
                ia.addEntiteDynamique(smick);

                corde = new Corde(this);


                for (int y = 4; y < 9; y++) {
                    addEntite(corde, 12, y);
                }

                colonne = new Colonne(this);
                for(int i = 4; i < 7; i++){
                    addEntite(colonne, 13, i);
                }

                initialisationdunecolone(8,1);
                initialisationdunecolone(1,1);
                initialisationdunecolone(13,5);


                ordonnanceur.add(g);
                ordonnanceur.add(ia);


                Controle4Directions.getInstance().addEntiteDynamique(hector);
                ordonnanceur.add(Controle4Directions.getInstance());

                // murs extérieurs horizontaux
                for (int x = 0; x < 20; x++) {
                    addEntite(new Mur(this, true), x, 0);
                    addEntite(new Mur(this, true), x, 9);
                }

                // murs extérieurs verticaux
                for (int y = 1; y < 9; y++) {
                    addEntite(new Mur(this, true), 0, y);
                    addEntite(new Mur(this, true), 19, y);
                }

                for (int x = 1; x < 12; x++) {
                    addEntite(new Mur(this, false), x, 6);
                    addEntite(new Mur(this, false), x, 6);
                }
*/
    }

    private void initialisationdunecolone(int x,int y){

        Colonne col1 = new Colonne(this,y+1,1);
        Colonne col2 = new Colonne(this,y+1,2);
        Colonne col3 = new Colonne(this,y+1,3);

        addEntite(col1,x,y+1);
        addEntite(col2,x,y+2);
        addEntite(col3,x,y+3);

        ControleColonne.getInstance().addEntiteDynamique(col1);
        ControleColonne.getInstance().addEntiteDynamique(col2);
        ControleColonne.getInstance().addEntiteDynamique(col3);

        ordonnanceur.add(ControleColonne.getInstance());

    }
    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }

    private void supprimerEntite(Entite e, int x, int y){
        grilleEntites[x][y] = null;
        map.remove(e);
    }
    
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        System.out.println(score);
        boolean retour = false;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);

        if (contenuDansGrille(pCible)&& ( objetALaPosition(pCible) == null) ||objetALaPosition(pCible).peutEtreTraverse()) {
            // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null ) {
                        cmptDeplV.put(e, 1);

                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;
                    }
                    break;
            }
        } else if(objetALaPosition(pCible).peutEtreRamasse()){
            score = score + 100;
            System.out.println("bomb");
            Entite entiteBombe = objetALaPosition(pCible);
            supprimerEntite(entiteBombe, pCible.x, pCible.y);
            deplacerEntite(pCourant, pCible, e);
        }

/*
        else if (contenuDansGrille(pCible) && objetALaPosition(pCible).peutEtreTraverse()) {
            hector.estSur = objetALaPosition(pCible);
            hector.alapos = pCible;
            hector.jeu.deplacerEntite(pCourant, pCible, hector);
            addEntite(hector.estSur, hector.alapos.x, hector.alapos.y);
        }
*/
        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        return retour;
    }

    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }

    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = e.estSur;
        map.put(e.estSur, pCourant);
        e.estSur = grilleEntites[pCible.x][pCible.y];
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }
        // On verifie si la case cible est (null ou est traversable) et que la case courante est aussi traversable alors on affecte ce qui est traversable a la position courante
        // SINON on affecte null
        /*if(((contenuDansGrille(pCible) && objetALaPosition(pCible) == null) || contenuDansGrille(pCible) && objetALaPosition(pCible).peutEtreTraverse() ) && contenuDansGrille(pCourant) && objetALaPosition(pCourant).peutEtreTraverse()){
            grilleEntites[pCourant.x][pCourant.y] = e.estSur;
            System.out.println("aaa   "+e.estSur);
        } else {
            grilleEntites[pCourant.x][pCourant.y] = null;
        }
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }*/
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
