package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.DataSourceProvider;
import com.mycompany.tennis.core.entity.Score;

import javax.sql.DataSource;
import java.sql.*;

public class ScoreRepositoryImpl {

    public void create(Score score){
        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            //MySQL driver MySQL Connector
            conn = dataSource.getConnection();

            PreparedStatement statement = conn.prepareStatement("INSERT INTO SCORE_VAINQUEUR (ID_MATCH,SET_1,SET_2,SET_3,SET_4,SET_5) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1,score.getMatch().getId());
            statement.setByte(2,score.getSet1());
            statement.setByte(3,score.getSet2());
            if(score.getSet3()==null){
                statement.setNull(4,Types.TINYINT);
            }
            else{
                statement.setByte(4,score.getSet3());
            }
            if(score.getSet4()==null){
                statement.setNull(5,Types.TINYINT);
            }
            else{
                statement.setByte(5,score.getSet4());
            }
            if(score.getSet5()==null){
                statement.setNull(6,Types.TINYINT);
            }
            else{
                statement.setByte(6,score.getSet5());
            }
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                score.setId(rs.getLong(1)); //position de la colonne dans la bdd
            }



            System.out.println("Score créé");
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
