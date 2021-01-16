package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.Entity.Joueur;
import com.mycompany.tennis.core.Entity.Tournoi;
import com.mycompany.tennis.core.repository.TournoiRepositoryImpl;

public class TournoiService {

    private TournoiRepositoryImpl tournoiRepository;

    public TournoiService() {
        this.tournoiRepository = new TournoiRepositoryImpl();
    }
    public Tournoi getTournoi(long id){
        return tournoiRepository.getById(id);
    }

    public void createTournoi(Tournoi tournoi){
        tournoiRepository.create(tournoi);
    }
}
