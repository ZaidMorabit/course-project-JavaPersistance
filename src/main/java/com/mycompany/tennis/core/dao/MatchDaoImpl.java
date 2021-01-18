package com.mycompany.tennis.core.dao;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.entity.Match;

import javax.sql.DataSource;
import java.sql.*;

public class MatchDaoImpl {

    public void createMatchWithScore(Match match){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

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



            PreparedStatement statement2 = conn.prepareStatement("INSERT INTO SCORE_VAINQUEUR (ID_MATCH,SET_1,SET_2,SET_3,SET_4,SET_5) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement2.setLong(1,match.getScore().getMatch().getId());
            statement2.setByte(2,match.getScore().getSet1());
            statement2.setByte(3,match.getScore().getSet2());
            if(match.getScore().getSet3()==null){
                statement2.setNull(4,Types.TINYINT);
            }
            else{
                statement2.setByte(4,match.getScore().getSet3());
            }
            if(match.getScore().getSet4()==null){
                statement2.setNull(5,Types.TINYINT);
            }
            else{
                statement2.setByte(5,match.getScore().getSet4());
            }
            if(match.getScore().getSet5()==null){
                statement2.setNull(6,Types.TINYINT);
            }
            else{
                statement2.setByte(6,match.getScore().getSet5());
            }
            statement2.executeUpdate();

            ResultSet rs2 = statement2.getGeneratedKeys();

            if(rs2.next()){
                match.getScore().setId(rs2.getLong(1)); //position de la colonne dans la bdd
            }



            System.out.println("Score créé");

            conn.commit();

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
