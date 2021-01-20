package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.entity.Score;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.*;

public class ScoreRepositoryImpl {

    public void create(Score score){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(score);
        System.out.println("Score créé");
    }

    public void delete(Long id){
        Score score = getById(id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(score);
        System.out.println("Score supprimé");
    }

    public Score getById(Long id){
        Session session = null;
        Score score = null;
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        score =  session.get(Score.class,id);
        System.out.println("Score lu");
        return score;
    }
}
