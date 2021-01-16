package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.Entity.Joueur;
import com.mycompany.tennis.core.repository.JoueurRepositoryImpl;

public class JoueurService {

    private JoueurRepositoryImpl joueurRepository;

    public JoueurService() {
        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public void createJoueur(Joueur joueur){
        joueurRepository.create(joueur);
    }

    public Joueur getJoueur(long id){
        return joueurRepository.getById(id);
    }
}
