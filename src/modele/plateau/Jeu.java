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
    private int time = 200;

    public int nb_bombes;
    private  int max_score;
    private int score = 0;
    private int vie = 3;

    private int nb_radis =0;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;

    private Bot smick;

    private Corde corde;

    private Colonne colonne;

    private Bombe bombe;
    private boolean jeu_fini;


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
    /**
     * @fn int getScore()
     * @brief getter getScore
     * @return score
     */
    public int getScore(){
        return score;
    }
    /**
     * @fn int getVie()
     * @brief getter getVie
     * @return vie
     */
    public int getVie(){
        return vie;
    }
    /**
     * @fn int getMax_score()
     * @brief getter max_score
     * @return max_score
     */
    public int getMax_score(){
        return max_score;
    }
  /**
  * @fn initialisationDesEntites()
  * @brief Fonction qui lit un fichier texte, puis en fonction des caractères lu sur celui si, places les entités sur le terrain
  */
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
                    case 'r' :
                        addEntite(new Radis(this), x, y);
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
            set_timer(200);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @fn int set_timer(int timer)
     * @brief setter du timer
     * @param timer : int
     * @return time : int
     */
    public int set_timer(int timer){
        time = timer;
        return time;
    }
    /**
     * @fn int get_timer()
     * @brief getter du timer
     * @return time
     */
    public int get_timer(){
        return time;
    }
    /**
     * @fn boolean est_fini_perd()
     * @brief si hector n'a plus de vie alors il perd
     */
    public boolean est_fini_perd(){
        if (hector.getVivant() == false){
            vie = vie-1;
            hector.setVivant(true);
            if(vie == 0){
                write_maxscore();
                fini = true;
            }
        }
        return true;
    }
    /**
     * @fn boolean est_fini_gagne()
     * @brief si il ne reste plus de bombe alors hector gagne, et on ecrit le score avec write_maxscore();
     */
    public boolean est_fini_gagne(){
        if(nb_bombes == 0){
            write_maxscore();
            return true;
        }
        return false;
    }

    public boolean peut_mettre_radis(){
        if(nb_radis == 1   ){


        }
        return true;
    }

    /**
     * @fn void reset()
     * @brief fonction qui réinitialise les entités
     */
    public void reset(){
        ordonnanceur.clear();
        map.clear();
        ControleColonne.resetColBleu();
        ControleColonne.resetColRouge();
        Controle4Directions.reset();
        cmptDeplH.clear();
        cmptDeplV.clear();
        for(int i = 0 ; i < SIZE_X; i++){
            for(int j = 0; j < SIZE_Y; j++){
                grilleEntites[i][j] = null;
            }
        }
    }
    /**
     * @fn void resetlvl()
     * @brief fonction qui réinitialise le niveau
     */
    public void resetlvl(){
        reset();
        initialisationDesEntites();

    }
    /**
     * @fn void lvlfini()
     * @brief fonction qui réinitialise le niveau quand il est fini et qui incrémente le niveau actuelle à jouer,
     * si il n'y a plus de niveau alors il affiche l'écran de victoire, et affiche dans la console qu'il n'y a plus de lvl.
     */
    public void lvlfini(){
        reset();
        if (lvl < 3)
            lvl = lvl+1;
        else{
            set_est_fini();
            System.out.println("Plus de niveau");
        }

        initialisationDesEntites();
        start(300);
    }

    private void initialisationdunecolone(int x,int y,int n){

        if (n == 0 ) {
            y=y-1;
            Colonne col1 = new Colonne(this, y + 1, 1, n);
            Colonne col2 = new Colonne(this, y + 1, 2, n);
            Colonne col3 = new Colonne(this, y + 1, 3, n);

            addEntite(col1, x, y + 1);
            addEntite(col2, x, y + 2);
            addEntite(col3, x, y + 3);

            ControleColonne.getInstanceBleu().addEntiteDynamique(col1);
            ControleColonne.getInstanceBleu().addEntiteDynamique(col2);
            ControleColonne.getInstanceBleu().addEntiteDynamique(col3);
            ordonnanceur.add(ControleColonne.getInstanceBleu());

        }

        if (n == 1 ) {
            y=y-1;
            Colonne col1 = new Colonne(this, y + 1, 1,n);
            Colonne col2 = new Colonne(this, y + 1, 2, n);
            Colonne col3 = new Colonne(this, y + 1, 3, n);

            addEntite(col1, x, y + 1);
            addEntite(col2, x, y + 2);
            addEntite(col3, x, y + 3);

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

        if(e instanceof Heros){
            System.out.println("a pCourant je suis " + objetALaPosition(pCourant) + " et a pCible je suis " + objetALaPosition(pCible));

        }

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
        }else if(objetALaPosition(pCible).peutEtreRamasse() && e.peutRamasser() && !objetALaPosition(pCible).peutDistraire()){
            System.out.println("je suis une bombe ?");
            score = score + 100;
            nb_bombes--;
            est_fini_gagne();
            Entite entiteBombe = objetALaPosition(pCible);
            supprimerEntite(entiteBombe, pCible.x, pCible.y);
            deplacerEntite(pCourant, pCible, e);
            } /*else if(objetALaPosition(pCible).peutEtreRamasse() &&  objetALaPosition(pCible).peutDistraire()){
                System.out.println("RADISS");
                nb_radis++;
                Entite eRadis = objetALaPosition(pCible);
                supprimerEntite(eRadis, pCible.x, pCible.y);
                deplacerEntite(pCourant, pCible, e);

            }*/
            else if(objetALaPosition(pCible) instanceof  Bombe){
            System.out.println("bomba");
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
    /**
     * @fn int setVie(int a)
     * @brief setter vie
     * @param a : int
     * @return vie
     */
    public int setVie(int a){
        vie = a;
        return vie;
    }
    /**
     * @fn int getlvl()
     * @brief getter getlvl
     * @return lvl
     */
    public int getlvl(){
        return lvl;
    }
    /**
     * @fn get_est_fini()
     * @brief getter jeu_fini
     * @return jeu_fini
     */
    public boolean get_est_fini(){
        return  jeu_fini;
    }
    /**
     * @fn set_est_fini()
     * @brief setter jeu_fini
     * @return jeu_fini
     */
    public boolean set_est_fini(){
        jeu_fini = true;
        return jeu_fini;
    }
    /**
     * @fn void write_maxscore()
     * @brief écrit les entier du fichier maxscore.txt
     */
    public void write_maxscore() {
        score = score + time;
        if (score > max_score) {
            max_score = score;
            String smax_score = Integer.toString(max_score);
            try {
                FileWriter myWriter = new FileWriter("src/levels/max_score.txt");
                myWriter.write(smax_score);
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @fn void read_maxscore()
     * @brief lis les entier du fichier maxscore.txt
     */
    public void read_maxscore() throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader("src/levels/max_score.txt"));

        String Int_line;

        while ((Int_line = reader.readLine()) != null) {
            int In_Value = Integer.parseInt(Int_line);
            max_score = In_Value;// Print the Integer
        }
    }


    /*public boolean ramassageEntite(EntiteDynamique ed, Direction d, Ramassage r){
        System.out.println("radi?");
        return true;
    }*/
}
