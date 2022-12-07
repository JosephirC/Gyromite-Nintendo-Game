/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.*;
import modele.deplacements.ControleColonne;

import java.awt.Point;
import java.io.*;
import java.util.HashMap;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    public int lvl;

    public int nb_bombes;
    private  int max_score;
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

    public boolean fini = false;
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

    public int getVie(){
        return vie;
    }

    public int getMax_score(){
        return max_score;
    }

    private void initialisationDesEntites() {
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
                switch ((char)r) {
                    case 'p':
                        addEntite(new Bombe(this), x, y);
                        nb_bombes++;
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
                    case 'c':
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
                    case 'i':
                        smick = new Bot(this);
                        addEntite(smick, x, y);
                        g.addEntiteDynamique(smick);
                        ia.addEntiteDynamique(smick);
                        x++;
                        break;
                    case 'l':
                        initialisationdunecolone(x, y, 0);
                        x++;
                        break;
                    case 'L':
                        initialisationdunecolone(x, y, 1);
                        x++;
                        break;
                }
            }
            ordonnanceur.add(g);
            ordonnanceur.add(ia);
            read_maxscore();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean est_fini_perd(){
        if (hector.vivant == false){
            vie = vie-1;
            hector.vivant = true;
            System.out.println(vie);
            if(vie == 0){
                fini = true;
            }
        }
        return true;
    }
    public boolean est_fini_gagne(){
        if(nb_bombes == 0){
            write_maxscore();
            return true;
        }
        return false;
    }

    public void lvlfini(){
        ordonnanceur.clear();
        map.clear();
        ControleColonne.resetb();
        ControleColonne.resetr();
        Controle4Directions.reset();
        cmptDeplH.clear();
        cmptDeplV.clear();
        for(int i = 0 ; i < SIZE_X; i++){
            for(int j = 0; j < SIZE_Y; j++){
                grilleEntites[i][j] = null;
            }
        }
        lvl = lvl+1;

        initialisationDesEntites();
        start(300);
    }

    private void initialisationdunecolone(int x,int y, int col){

        if(col == 1){
            Colonne col1 = new Colonne(this,y+1,1, col);
            Colonne col2 = new Colonne(this,y+1,2, col);
            Colonne col3 = new Colonne(this,y+1,3, col);

            addEntite(col1,x,y+1);
            addEntite(col2,x,y+2);
            addEntite(col3,x,y+3);

            ControleColonne.getInstanceBleu().addEntiteDynamique(col1);
            ControleColonne.getInstanceBleu().addEntiteDynamique(col2);
            ControleColonne.getInstanceBleu().addEntiteDynamique(col3);
            ordonnanceur.add(ControleColonne.getInstanceBleu());
        } else {

            Colonne col1 = new Colonne(this,y+1,1, col);
            Colonne col2 = new Colonne(this,y+1,2, col);
            Colonne col3 = new Colonne(this,y+1,3, col);

            addEntite(col1,x,y+1);
            addEntite(col2,x,y+2);
            addEntite(col3,x,y+3);

            ControleColonne.getInstanceRouge().addEntiteDynamique(col1);
            ControleColonne.getInstanceRouge().addEntiteDynamique(col2);
            ControleColonne.getInstanceRouge().addEntiteDynamique(col3);
            ordonnanceur.add(ControleColonne.getInstanceRouge());
        }
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
        //System.out.println(score);
        boolean retour = false;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);

    /*
    Si (je suis null) ou si (je peux etre traverser ET (si je ne peux pas etre ramasser ou si je ne peux pas ramasser ))
     */
        if ( (contenuDansGrille(pCible)&& ( objetALaPosition(pCible) == null) )|| (objetALaPosition(pCible).peutEtreTraverse() && (!objetALaPosition(pCible).peutEtreRamasse() || !e.peutRamasser()))) {

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
        }else if(objetALaPosition(pCible).peutEtreRamasse() && e.peutRamasser()){
            score = score + 100;
            nb_bombes--;
            est_fini_gagne();
            Entite entiteBombe = objetALaPosition(pCible);
            supprimerEntite(entiteBombe, pCible.x, pCible.y);
            deplacerEntite(pCourant, pCible, e);
        }
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

    public void write_maxscore() {
        if (score > max_score) {
            max_score = score;
            String smax_score = Integer.toString(max_score);
            try {
                FileWriter myWriter = new FileWriter("src/levels/max_score.txt");
                myWriter.write(smax_score);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public void read_maxscore() throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader("src/levels/max_score.txt"));

        String Int_line;

        while ((Int_line = reader.readLine()) != null) {
            int In_Value = Integer.parseInt(Int_line);
            max_score = In_Value;// Print the Integer
        }
        System.out.println("Max score : " + max_score);
    }
}
