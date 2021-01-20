package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.dto.*;
import com.mycompany.tennis.core.entity.Score;
import com.mycompany.tennis.core.repository.ScoreRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ScoreService {

    private ScoreRepositoryImpl scoreRepository;

    public ScoreService() {
        this.scoreRepository = new ScoreRepositoryImpl();
    }

    public void delete(Long id){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            scoreRepository.delete(id);
            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
        }
        finally {
            if(session != null){
                session.close();
            }
        }
    }

    public ScoreFullDto getScore(Long id){
        Session session = null;
        Transaction tx = null;
        ScoreFullDto scoreDto = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            Score score =  scoreRepository.getById(id);

            scoreDto = new ScoreFullDto();
            scoreDto.setId(score.getId());
            scoreDto.setSet1(score.getSet1());
            scoreDto.setSet2(score.getSet2());
            scoreDto.setSet3(score.getSet3());
            scoreDto.setSet4(score.getSet4());
            scoreDto.setSet5(score.getSet5());

            MatchDto matchDto = new MatchDto();
            matchDto.setId(score.getMatch().getId());

            scoreDto.setMatch(matchDto);

            EpreuveFullDto epreuveDto = new EpreuveFullDto();
            epreuveDto.setId(score.getMatch().getEpreuve().getId());
            epreuveDto.setAnnee(score.getMatch().getEpreuve().getAnnee());
            epreuveDto.setTypeEpreuve(score.getMatch().getEpreuve().getTypeEpreuve());
            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(score.getMatch().getEpreuve().getTournoi().getId());
            tournoiDto.setNom(score.getMatch().getEpreuve().getTournoi().getNom());
            tournoiDto.setCode(score.getMatch().getEpreuve().getTournoi().getCode());
            epreuveDto.setTournoi(tournoiDto);
            matchDto.setEpreuve(epreuveDto);

            JoueurDto vainqueurDto = new JoueurDto();
            vainqueurDto.setId(score.getMatch().getVainqueur().getId());
            vainqueurDto.setNom(score.getMatch().getVainqueur().getNom());
            vainqueurDto.setPrenom(score.getMatch().getVainqueur().getPrenom());
            vainqueurDto.setSexe(score.getMatch().getVainqueur().getSexe());
            matchDto.setVainqueur(vainqueurDto);

            JoueurDto finalisteDto = new JoueurDto();
            finalisteDto.setId(score.getMatch().getFinaliste().getId());
            finalisteDto.setNom(score.getMatch().getFinaliste().getNom());
            finalisteDto.setPrenom(score.getMatch().getFinaliste().getPrenom());
            finalisteDto.setSexe(score.getMatch().getFinaliste().getSexe());
            matchDto.setFinaliste(finalisteDto);





            tx.commit();
        }catch (Exception e){
            if(tx !=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }

        return scoreDto;
    }

}
