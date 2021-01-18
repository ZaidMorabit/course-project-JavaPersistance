package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.entity.Tournoi;
import org.hibernate.Session;
import com.mycompany.tennis.core.HibernateUtil;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournoiRepositoryImpl {
    public void create(Tournoi tournoi){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(tournoi);
            tx.commit();

        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {

        }

    }

    public void update(Tournoi tournoi){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.update(tournoi);
    }

    public void delete(long id){
        Tournoi tournoi = new Tournoi();
        tournoi.setId(id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.delete(tournoi);
        System.out.println("Tournoi supprimé");
    }

    public Tournoi getById(long id){
        Session session=null;
        Tournoi tournoi=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tournoi = session.get(Tournoi.class,id);


            System.out.println("Tournoi lu");
        } catch (Throwable t) {
            t.printStackTrace();

        }
        finally {
            if (session!= null) {
                session.close();
            }
        }
        return tournoi;
    }

    public List<Tournoi> list(){
        Connection conn = null;
        List<Tournoi> tournois = new ArrayList<>();
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("SELECT ID,NOM,CODE FROM TOURNOI");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Tournoi tournoi= new Tournoi();
                tournoi.setId(rs.getLong("id"));
                tournoi.setNom(rs.getString("nom"));
                tournoi.setCode(rs.getString("code"));
                tournois.add(tournoi);
            }


            System.out.println("Tournois lus");
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                if(conn!=null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        finally {
            try {
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tournois;
    }
}
