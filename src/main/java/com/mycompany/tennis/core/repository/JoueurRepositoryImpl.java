package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.Entity.Joueur;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurRepositoryImpl {

    public void create(Joueur joueur){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO JOUEUR (NOM,PRENOM,SEXE) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,joueur.getNom());
            statement.setString(2,joueur.getPrenom());
            statement.setString(3,joueur.getSexe().toString());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                joueur.setId(rs.getLong(1)); //position de la colonne dans la bdd
            }



            System.out.println("Joueur créé");
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
        Connection conn = null;
        Joueur joueur=null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("SELECT NOM,PRENOM,SEXE FROM JOUEUR WHERE ID=?");

            statement.setLong(1,id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                joueur = new Joueur();
                joueur.setId(id);
                joueur.setNom(rs.getString("nom"));
                joueur.setPrenom(rs.getString("prenom"));
                joueur.setSexe(rs.getString("sexe").charAt(0));
            }


            System.out.println("Joueur lu");
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
