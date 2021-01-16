package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.Entity.Joueur;
import com.mycompany.tennis.core.Entity.Match;

import javax.sql.DataSource;
import java.sql.*;

public class MatchRepositoryImpl {

        public void create(Match match){
            Connection conn = null;
            try {
                DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

                //MySQL driver MySQL Connector
                conn = dataSource.getConnection();

                PreparedStatement statement = conn.prepareStatement("INSERT INTO MATCH_TENNIS (ID_EPREUVE,ID_VAINQUEUR,ID_FINALISTE) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

                statement.setLong(1,match.getEpreuve().getId());
                statement.setLong(2,match.getVainqueur().getId());
                statement.setLong(3,match.getFinaliste().getId());

                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                if(rs.next()){
                    match.setId(rs.getLong(1)); //position de la colonne dans la bdd
                }



                System.out.println("Match créé");
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
}
