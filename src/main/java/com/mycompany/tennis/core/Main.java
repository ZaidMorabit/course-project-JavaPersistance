package com.mycompany.tennis.core;

import com.mycompany.tennis.core.Entity.*;
import com.mycompany.tennis.core.repository.JoueurRepositoryImpl;
import com.mycompany.tennis.core.repository.TournoiRepositoryImpl;
import com.mycompany.tennis.core.service.JoueurService;
import com.mycompany.tennis.core.service.MatchService;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class Main {
    public static void main(String... args){
        JoueurService joueurService = new JoueurService();
        /*Joueur noah = new Joueur();
        noah.setNom("Noah");
        noah.setPrenom("Yannick");
        noah.setSexe('H');
        joueurService.createJoueur(noah);

        System.out.println("id cree: " +noah.getId()); */

//        Joueur joueur = joueurService.getJoueur(5);
//
//        System.out.println(joueur.getNom() +" " + joueur.getPrenom());

        MatchService matchService = new MatchService();
        Match match = new Match();
        Score score = new Score();
        score.setSet1((byte)3);
        score.setSet2((byte)4);
        score.setSet3((byte)6);

        match.setScore(score);
        score.setMatch(match);

        Joueur federer = new Joueur();
        federer.setId(32L);
        Joueur murray = new Joueur();
        murray.setId(34L);

        match.setVainqueur(federer);
        match.setFinaliste(murray);

        Epreuve epreuve = new Epreuve();
        epreuve.setId(10L);
        match.setEpreuve(epreuve);

        matchService.enregistrerNouveauMatch(match);


    }
}