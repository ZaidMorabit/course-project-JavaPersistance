package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.entity.Joueur;
import org.hibernate.Session;
import com.mycompany.tennis.core.HibernateUtil;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepositoryImpl {

    public void renome(Long id, String nouveauNom){
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
    }

    public void create(Joueur joueur){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession(); //recupere une session vide Hibernate
            tx = session.beginTransaction(); // il faut definir une transaction pour ecrire dans la bdd (execute con.autocommit(false) dans la session)
            /*
            methode à appler dans la majorité des cas pour faire un insert.
            Rends l'etat de l'objet de transient à persistent dans la session
            Mais persist ne suffit pas pour ecrire dans la base de donnée
            */
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
    }

    public void update(Joueur joueur){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?,PRENOM=?,SEXE=? WHERE ID=?");

            statement.setString(1,joueur.getNom());
            statement.setString(2,joueur.getPrenom());
            statement.setString(3,joueur.getSexe().toString());
            statement.setLong(4,joueur.getId());

            statement.executeUpdate();



            System.out.println("Joueur modifié");
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
    }

    public void delete(long id){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            statement.setLong(1,id);

            statement.executeUpdate();



            System.out.println("Joueur supprimé");
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
    }

    public Joueur getById(long id){
        Session session=null;
        Joueur joueur=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            joueur = session.get(Joueur.class,id);
            System.out.println("Joueur lu");
        }
        catch(Throwable t){
            t.printStackTrace();
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return joueur;
    }

    public List<Joueur> list(){
        Connection conn = null;
        List<Joueur> joueurs = new ArrayList<>();
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("SELECT ID,NOM,PRENOM,SEXE FROM JOUEUR");

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Joueur joueur = new Joueur();
                joueur.setId(rs.getLong("id"));
                joueur.setNom(rs.getString("nom"));
                joueur.setPrenom(rs.getString("prenom"));
                joueur.setSexe(rs.getString("sexe").charAt(0));
                joueurs.add(joueur);
            }


            System.out.println("Joueurs lus");
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
        return joueurs;
    }

}
