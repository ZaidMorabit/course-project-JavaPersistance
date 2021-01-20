package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.entity.Match;
import org.hibernate.Session;

import javax.sql.DataSource;
import java.sql.*;

public class MatchRepositoryImpl {

        public void create(Match match){
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.persist(match);
            System.out.println("Match créé");
        }

    public void delete(long id){
        Match match= getById(id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.delete(match);
        System.out.println("Match supprimé");
    }

        public Match getById(Long id){
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Match match = session.get(Match.class,id);
            System.out.println("Match lu");
            return match;
        }
}
