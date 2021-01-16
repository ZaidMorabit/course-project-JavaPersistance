package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.Entity.Joueur;
import com.mycompany.tennis.core.Entity.Tournoi;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournoiRepositoryImpl {
    public void create(Tournoi tournoi){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO TOURNOI (NOM,CODE) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,tournoi.getNom());
            statement.setString(2,tournoi.getCode());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                tournoi.setId(rs.getLong(1)); //position de la colonne dans la bdd
            }



            System.out.println("Tournoi créé");
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

    public void update(Tournoi tournoi){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("UPDATE TOURNOI SET NOM=?,CODE=? WHERE ID=?");

            statement.setString(1,tournoi.getNom());
            statement.setString(2,tournoi.getCode());
            statement.setLong(3,tournoi.getId());

            statement.executeUpdate();



            System.out.println("Tournoi modifié");
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

            PreparedStatement statement = conn.prepareStatement("DELETE FROM TOURNOI WHERE ID=?");

            statement.setLong(1,id);

            statement.executeUpdate();



            System.out.println("Tournoi supprimé");
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

    public Tournoi getById(long id){
        Connection conn = null;
        Tournoi tournoi=null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("SELECT NOM,CODE FROM TOURNOI WHERE ID=?");

            statement.setLong(1,id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                tournoi = new Tournoi();
                tournoi.setId(id);
                tournoi.setNom(rs.getString("nom"));
                tournoi.setCode(rs.getString("code"));
            }


            System.out.println("Tournoi lu");
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
