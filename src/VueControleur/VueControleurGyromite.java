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

    JMenuItem score;

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

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_K: ControleColonne.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_N: ControleColonne.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_ESCAPE :
                        InteractionRamassage.getInstance().setRamassage(Ramassage.espace, Controle4Directions.getInstance().getDirection());
                        break;
                }
            }
        });
    }

    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/player_ca.png", 0, 0, 32, 44);
        icoBot = chargerIcone("Images/smick_ca.png", 0, 0, 35, 40);//chargerIcone("Images/Pacman.png");

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

        icoBombe = chargerIcone("Images/bomb_ca.png", 64, 0, 64, 64);
    }

    private void placerLesComposantsGraphiques() {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Monospaced", Font.BOLD, 15);
        score = new JMenuItem("Score : "+ jeu.getScore());
        score.setFont(font);
        score.setForeground(Color.WHITE);
        score.setBackground(Color.BLACK);
        menuBar.add(score);

        setJMenuBar(menuBar);

        setTitle("Gyromite");
        //setSize(sizeX*titlesize, sizeY*titlesize);
        setSize(380, 230);
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

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
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

                } else if (jeu.getGrille()[x][y] instanceof Colonne) {
                    if (((Colonne) jeu.getGrille()[x][y]).pos == 1)
                        if ((((Colonne) jeu.getGrille()[x][y]).posM == y))
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne1Mr);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne1Mb);
                        else
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne1r);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne1b);
                    if ((((Colonne) jeu.getGrille()[x][y]).pos == 2))
                        if (((Colonne) jeu.getGrille()[x][y]).posM == y)
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne2Mr);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne2Mb);
                        else
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne2r);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne2b);
                    if ((((Colonne) jeu.getGrille()[x][y]).pos == 3))
                        if (((Colonne) jeu.getGrille()[x][y]).posM == y)
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne3Mr);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne3Mb);
                        else
                            if (((Colonne) jeu.getGrille()[x][y]).couleur == 0 ) // if couleur
                                tabJLabel[x][y].setIcon(icoColonne3r);
                            else                                                 // else couleur
                                tabJLabel[x][y].setIcon(icoColonne3b);
                }else if (jeu.getGrille()[x][y] instanceof Corde){
                    tabJLabel[x][y].setIcon(icoCorde);
                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }

                updateScore(jeu);
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

    private void updateScore(Jeu jeu){
        score.setText("Score : "+ jeu.getScore());
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
