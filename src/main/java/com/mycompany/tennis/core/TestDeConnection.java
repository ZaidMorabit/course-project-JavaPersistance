package com.mycompany.tennis.core;

import com.mycompany.tennis.core.Entity.Joueur;
import com.mycompany.tennis.core.Entity.Tournoi;
import com.mycompany.tennis.core.repository.JoueurRepositoryImpl;
import com.mycompany.tennis.core.repository.TournoiRepositoryImpl;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class TestDeConnection {
    public static void main(String... args){
        TournoiRepositoryImpl tournoiRepository = new TournoiRepositoryImpl();
        for(Tournoi tournoi : tournoiRepository.list()){
            System.out.println(tournoi.getNom() + " " + tournoi.getCode());
        }

    }
}

