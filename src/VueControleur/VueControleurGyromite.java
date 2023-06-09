package VueControleur;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import modele.deplacements.*;
import modele.plateau.*;




/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int titlesize = 16;
    private int sizeX; // taille de la grille affichée
    private int sizeY;

    private JMenuItem score;
    private JMenuItem max_score;

    private JMenuItem vie;
    private JMenuItem timer;
    private JMenuItem affichage;

    private JMenuItem scoretemps;
    private ImageIcon gameOverScreen;

    private ImageIcon gameWinScreen;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoBot;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoColonne1b;
    private ImageIcon icoColonne2b;
    private ImageIcon icoColonne3b;

    private ImageIcon icoColonne1Mb;
    private ImageIcon icoColonne2Mb;
    private ImageIcon icoColonne3Mb;


    private ImageIcon icoColonne1r;
    private ImageIcon icoColonne2r;
    private ImageIcon icoColonne3r;
    private ImageIcon icoColonne1Mr;
    private ImageIcon icoColonne2Mr;
    private ImageIcon icoColonne3Mr;
    private ImageIcon icoBrique;
    private ImageIcon icoCorde;

    private ImageIcon icoBombe;
    private ImageIcon icoRadis;


    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)

    private AudioInputStream audioInputStream;
    private Clip clip;
    private FloatControl gainControl;

    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        lireMusique();
        ajouterEcouteurClavier();
    }

    /**
     * @fn void ajouterEcouteurClavier()
     * @brief permet interaction au clavier
     */
    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_S: ControleColonne.getInstanceBleu().setDirectionCouranteB(Direction.haut, 0); break;
                    case KeyEvent.VK_D: ControleColonne.getInstanceBleu().setDirectionCouranteB(Direction.bas, 0); break;
                    case KeyEvent.VK_X: ControleColonne.getInstanceRouge().setDirectionCouranteR(Direction.haut, 1); break;
                    case KeyEvent.VK_C: ControleColonne.getInstanceRouge().setDirectionCouranteR(Direction.bas, 1); break;
                    case KeyEvent.VK_R: jeu.resetlvl(); break;
                    case KeyEvent.VK_ENTER:
                    case KeyEvent.VK_SHIFT:
                        System.out.println('\n');
                    /*case KeyEvent.VK_ESCAPE :
                        InteractionRamassage.getInstance().setRamassage(Ramassage.espace, Controle4Directions.getInstance().getDirection());
                        break;*/
                }
            }
        });
    }

    /**
     * @fn void chargerLesIcones()
     * @brief permet de charger Les Icones
     */
    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/sprites.png", 0, 0, 25, 25);
        icoBot = chargerIcone("Images/sprites.png", 0, 140, 25, 25);//chargerIcone("Images/Pacman.png");

        icoVide = chargerIcone("Images/bg.png");

        icoColonne1b = chargerIcone("Images/tileset.png", 0, 48, 16, 16);
        icoColonne2b = chargerIcone("Images/tileset.png", 16, 48, 16, 16);
        icoColonne3b = chargerIcone("Images/tileset.png", 32, 48, 16, 16);
        icoColonne1Mb = chargerIcone("Images/tileset.png", 0, 32, 16, 16);
        icoColonne2Mb = chargerIcone("Images/tileset.png", 16, 32, 16, 16);
        icoColonne3Mb = chargerIcone("Images/tileset.png", 32, 32, 16, 16);


        icoColonne1r = chargerIcone("Images/tileset.png", 0, 48+32, 16, 16);
        icoColonne2r = chargerIcone("Images/tileset.png", 16, 48+32, 16, 16);
        icoColonne3r = chargerIcone("Images/tileset.png", 32, 48+32, 16, 16);
        icoColonne1Mr = chargerIcone("Images/tileset.png", 0, 32+32, 16, 16);
        icoColonne2Mr = chargerIcone("Images/tileset.png", 16, 32+32, 16, 16);
        icoColonne3Mr = chargerIcone("Images/tileset.png", 32, 32+32, 16, 16);


        icoMur = chargerIcone("Images/tileset.png", 0, 0, 16, 16);
        icoBrique = chargerIcone("Images/tileset.png", 32,0,16,16);
        icoCorde = chargerIcone("Images/tileset.png", 16, 0, 16, 16);

        icoBombe = chargerIcone("Images/sprites.png", 0, 250, 25, 25);
        icoRadis = chargerIcone("Images/sprites.png", 73, 250, 15, 20 );

        gameWinScreen = chargerIcone("Images/game-win.png");
        gameOverScreen= chargerIcone("Images/game-over.png");

    }

    /**
     * @fn void placerLesComposantsGraphiques()
     * @brief permet de placer Les Composants Graphiques
     */
    private void placerLesComposantsGraphiques() {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Monospaced", Font.BOLD, 11);

        /*score = new JMenuItem("Score: "+ jeu.getScore());
        score.setFont(font);
        score.setForeground(Color.WHITE);
        score.setBackground(Color.BLACK);
        menuBar.add(score);

        vie = new JMenuItem("Vie: " + jeu.getVie());
        vie.setFont(font);
        vie.setForeground(Color.WHITE);
        vie.setBackground(Color.BLACK);
        menuBar.add(vie);




        max_score = new JMenuItem("M_Score: " + jeu.getMax_score());
        max_score.setFont(font);
        max_score.setForeground(Color.WHITE);
        max_score.setBackground(Color.BLACK);
        menuBar.add(max_score);*/



        affichage = new JMenuItem("Score: "+ jeu.getScore() + "Vie: " + jeu.getVie() +  "M_Score: " + jeu.getMax_score());
        affichage.setFont(font);
        affichage.setForeground(Color.WHITE);
        affichage.setBackground(Color.BLACK);
        menuBar.add(affichage);

        scoretemps = new JMenuItem("T: "+ jeu.get_timer() + "   M_Score: " + jeu.getMax_score());
        scoretemps.setFont(font);
        scoretemps.setForeground(Color.WHITE);
        scoretemps.setBackground(Color.BLACK);
        menuBar.add(scoretemps);

        setJMenuBar(menuBar);

        setTitle("Gyromite");
        //setSize(sizeX*titlesize, sizeY*titlesize);
        setSize(379, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();

                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {
        updateScoreTemps();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    if (jeu.get_est_fini()){
                        setGameWinScreen();
                        jeu.set_est_fini();

                    } else
                        tabJLabel[x][y].setIcon(icoHero);

                    // si transparence : images avec canal alpha + dessins manuels (voir ci-dessous + créer composant qui redéfinie paint(Graphics g)), se documenter
                    //BufferedImage bi = getImage("Images/smick.png", 0, 0, 20, 20);
                    //tabJLabel[x][y].getGraphics().drawImage(bi, 0, 0, null);

                } else if (jeu.getGrille()[x][y] instanceof Bot) {
                    tabJLabel[x][y].setIcon(icoBot);

                    /* ImageIcon imageIcon = new ImageIcon(imageAbsolutePath);
                    Image tmpImage = imageIcon.getImage();

                    BufferedImage image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                    image.getGraphics().drawImage(tmpImage, 0, 0, null);
                    tmpImage.flush();

                    return image;*/

                } else if (jeu.getGrille()[x][y] instanceof Mur) {
                    if(((Mur) jeu.getGrille()[x][y]).brique)
                        tabJLabel[x][y].setIcon(icoBrique);
                    else
                        tabJLabel[x][y].setIcon(icoMur);

                } else if(jeu.getGrille()[x][y] instanceof Bombe){
                    tabJLabel[x][y].setIcon(icoBombe);

                } else if(jeu.getGrille()[x][y] instanceof Radis){
                    tabJLabel[x][y].setIcon(icoRadis);

                }else if (jeu.getGrille()[x][y] instanceof Colonne) {
                    if (((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 1 && ((Colonne) jeu.getGrille()[x][y]).getCouleur() == 0)
                        if ((((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y))
                            //if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne1Mb);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne1b);

                    if (((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 2 && ((Colonne) jeu.getGrille()[x][y]).getCouleur() ==0)
                        if (((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y)
                            //if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne2Mb);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne2b);

                    if ((((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 1) && ((Colonne) jeu.getGrille()[x][y]).getCouleur() ==1)
                        if (((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y)
                            //if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne1Mr);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne1r);
                    if (((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 2 && ((Colonne) jeu.getGrille()[x][y]).getCouleur() ==1)
                        if (((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y)
                            tabJLabel[x][y].setIcon(icoColonne2Mr);
                        else
                            tabJLabel[x][y].setIcon(icoColonne2r);

                    if (((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 3 && ((Colonne) jeu.getGrille()[x][y]).getCouleur() ==0)
                        if (((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y)
                            tabJLabel[x][y].setIcon(icoColonne3Mb);
                        else
                            tabJLabel[x][y].setIcon(icoColonne3b);

                    if (((Colonne) jeu.getGrille()[x][y]).getComposanteColonne() == 3 &&  ((Colonne) jeu.getGrille()[x][y]).getCouleur() ==1)
                        if (((Colonne) jeu.getGrille()[x][y]).getColonneMilieu() == y)
                            tabJLabel[x][y].setIcon(icoColonne3Mr);
                        else
                            tabJLabel[x][y].setIcon(icoColonne3r);

                }else if (jeu.getGrille()[x][y] instanceof Corde){
                    tabJLabel[x][y].setIcon(icoCorde);
                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }

                /*updateScore(jeu);
                updateMaxScore(jeu);
                updateVie(jeu);*/
                //updatelvl();
                updateAffichage();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }

    private void updateScore(){
        score.setText("Score: "+ jeu.getScore());
    }

    private void updateMaxScore(){
        max_score.setText("M_Score: "+ jeu.getMax_score());
    }
    private void updateVie(){
        vie.setText("Vie: "+ jeu.getVie());
        jeu.est_fini_perd();
        if(jeu.getVie() == 0){
            setGameOverScreen();
            clip.stop();
            jeu.set_est_fini();
        }

    }
    private void updateAffichage(){
        affichage.setText("Score: "+ jeu.getScore() + "   Vie: " + jeu.getVie());
        jeu.est_fini_perd();
        if(jeu.getVie() == 0){
            setGameOverScreen();
            clip.stop();
            jeu.set_est_fini();
        }
    }
    private  void updateScoreTemps(){
        jeu.set_timer(jeu.get_timer() -1);
        if(jeu.get_timer() <= 0){
            jeu.setVie(0);
            setGameOverScreen();
            clip.stop();
            jeu.set_est_fini();
        }
        scoretemps.setText("T: "+ jeu.get_timer() + "   M_Score: " + jeu.getMax_score());
    }

    private void updatelvl() { jeu.getlvl();}

    private void updateTime(){
        jeu.set_timer(jeu.get_timer() -1);
        timer.setText("T: "+ jeu.get_timer());

    }
    /**
     * @fn void setGameOverScreen()
     * @brief affiche l'ecran de defaite
     */
    public void setGameOverScreen() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.black);
        JLabel gameOverLabel = new JLabel(gameOverScreen);
        add(gameOverLabel);
        setSize(379, 230);
        repaint();
        setVisible(true);
    }
    /**
     * @fn void setGameWinScreen()
     * @brief affiche l'ecran de victoire
     */
    public void setGameWinScreen() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.black);
        JLabel gameWinLabel = new JLabel(gameWinScreen);
        add(gameWinLabel);
        setSize(379, 230);
        repaint();
        setVisible(true);
    }
    /**
     * @fn void lireMusique()
     * @brief permet de lire la musique
     */
    private void lireMusique() {
        try{

            audioInputStream = AudioSystem.getAudioInputStream(new File("Musique/gyromite-music.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f); // Reduit le volume de 30 decibels.
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch(IOException e){
            e.printStackTrace();
        } catch(LineUnavailableException e){
            e.printStackTrace();
        } catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }
    }

    // chargement de l'image entière comme icone
    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


        return new ImageIcon(image);
    }

    // chargement d'une sous partie de l'image
    private ImageIcon chargerIcone(String urlIcone, int x, int y, int w, int h) {

        // charger une sous partie de l'image à partir de ses coordonnées dans urlIcone
        BufferedImage bi = getSubImage(urlIcone, x, y, w, h);

        // adapter la taille de l'image a la taille du composant (ici : 20x20)
        return new ImageIcon(bi.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
    }

    private BufferedImage getSubImage(String urlIcone, int x, int y, int w, int h) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        BufferedImage bi = image.getSubimage(x, y, w, h);
        return bi;
    }

    /*private ImageIcon chargerHeros(Direction d){
        switch (d){
            case bas :
                icoHero = chargerIcone("Images/player_ca.png", 160, 0, 32, 44);
                break;
            case droite:
                icoHero = chargerIcone("Images/player_ca.png", 0, 0, 32, 44);
                break;
            case gauche :
                icoHero = chargerIcone("Images/player_ca.png", 32, -32, 32, 44);
                break;
        }
        return icoHero;
    }*/

}
