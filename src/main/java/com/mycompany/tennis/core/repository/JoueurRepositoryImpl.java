package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.EntityManagerHolder;
import com.mycompany.tennis.core.entity.Joueur;
import org.hibernate.Session;
import com.mycompany.tennis.core.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepositoryImpl {

    /*public void renome(Long id, String nouveauNom){
        Session session=null;
        Joueur joueur=null;
        Transaction tx =null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            joueur = session.get(Joueur.class,id);
            joueur.setNom(nouveauNom);
            tx.commit();
            System.out.println("Nom du joueur modifié");
        } catch(Exception e){
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
    }*/

    /*public void create(Joueur joueur){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession(); //recupere une session vide Hibernate
            tx = session.beginTransaction(); // il faut definir une transaction pour ecrire dans la bdd (execute con.autocommit(false) dans la session)
            *//*
            methode à appler dans la majorité des cas pour faire un insert.
            Rends l'etat de l'objet de transient à persistent dans la session
            Mais persist ne suffit pas pour ecrire dans la base de donnée
            *//*
            session.persist(joueur);
            //session.flush(); //synchronise l'etat de la session avec la bdd en effectuant les requetes sql et execute con.commit()
            tx.commit(); //declenche le flush de la session. preferable d'appeler cette instruction et laisser hibernate faire le flush

            System.out.println("Joueur créé");
        } catch(Exception e){
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
    }*/

    public void create(Joueur joueur){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(joueur);
        System.out.println("Joueur créé");
        }

    public void delete(long id){

        Joueur joueur = getById(id);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.delete(joueur);

        System.out.println("Joueur supprimé");
    }

    public Joueur getById(long id){
        Session session=null;
        Joueur joueur=null;

        //on recupere une session courrante et pas une session neuve car quand on effectue un update on appelle cette methode
        //il faut avoir la meme session que la methode update si on veut partager les objets qui y sont stocké pour les push apres dans la bdd
        // le scope de la session est configurée dans le fichier hibernae.cfg.xml
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        joueur = session.get(Joueur.class,id);
        System.out.println("Joueur lu");

        return joueur;
    }

    public List<Joueur> list(char sexe){
        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //Query<Joueur> query = session.createQuery("select j from Joueur j where j.sexe=?0",Joueur.class); //on peut preciser l'index du param juste apres le ?
        TypedQuery<Joueur> query = em.createQuery("select j from Joueur j where j.sexe=?0",Joueur.class); //on peut preciser l'index du param juste apres le ?
        query.setParameter(0,sexe);
        List<Joueur> joueurs = query.getResultList();
        System.out.println("Joueurs lu");
        return joueurs;
    }

}
