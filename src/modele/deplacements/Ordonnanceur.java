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

        while(true) {
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
                break;
            }
            if (jeu.get_est_fini()){
                break;
            }
        }

        if (jeu.est_fini_gagne()){
            jeu.lvlfini();
        }
    }
}
