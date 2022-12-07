package modele.deplacements;

import VueControleur.VueControleurGyromite;
import modele.plateau.Jeu;

import java.util.ArrayList;
import java.util.Observable;

import static java.lang.Thread.*;

public class Ordonnanceur extends Observable implements Runnable {
    private Jeu jeu;
    private ArrayList<RealisateurDeDeplacement> lstDeplacements = new ArrayList<RealisateurDeDeplacement>();
    private long pause;
    public void add(RealisateurDeDeplacement deplacement) {
        lstDeplacements.add(deplacement);
    }

    public Ordonnanceur(Jeu _jeu) {
        jeu = _jeu;
    }
    public void clear() {
        lstDeplacements.clear();
    }

    public void start(long _pause) {
        pause = _pause;
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean update = false;

        while(!jeu.fini) {
            jeu.resetCmptDepl();
            for (RealisateurDeDeplacement d : lstDeplacements) {
                if (d.realiserDeplacement())
                    update = true;
            }

            Controle4Directions.getInstance().resetDirection();
            //InteractionRamassage.getInstance().resetRamassage();

            if (update) {
                setChanged();
                notifyObservers();
            }

            try {
                sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (jeu.est_fini_gagne()){
                System.out.println(jeu.lvl);
                break;
            }
        }
        if (!jeu.getHector().vivant)
        System.out.println("t'es mort");
        if (jeu.est_fini_gagne()){
            System.out.println("t'as win");
            jeu.lvlfini();
        }
    }
}
