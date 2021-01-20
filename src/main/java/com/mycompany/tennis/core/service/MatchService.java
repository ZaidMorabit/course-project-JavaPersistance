package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.dto.*;
import com.mycompany.tennis.core.entity.Joueur;
import com.mycompany.tennis.core.entity.Match;
import com.mycompany.tennis.core.entity.Score;
import com.mycompany.tennis.core.repository.EpreuveRepositoryImpl;
import com.mycompany.tennis.core.repository.JoueurRepositoryImpl;
import com.mycompany.tennis.core.repository.MatchRepositoryImpl;
import com.mycompany.tennis.core.repository.ScoreRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MatchService {

    private ScoreRepositoryImpl scoreRepository;
    private MatchRepositoryImpl matchRepository;
    private EpreuveRepositoryImpl epreuveRepository;
    private JoueurRepositoryImpl joueurRepository;

//    private MatchDaoImpl matchDao;
    public MatchService() {
        this.scoreRepository = new ScoreRepositoryImpl();
        this.matchRepository = new MatchRepositoryImpl();
        this.epreuveRepository = new EpreuveRepositoryImpl();
        this.joueurRepository = new JoueurRepositoryImpl();
//        this.matchDao = new MatchDaoImpl();
    }

    public void enregistrerNouveauMatch(Match match){
        matchRepository.create(match);
        scoreRepository.create(match.getScore());
        //matchDao.createMatchWithScore(match);

    }

    public void delete(Long id){
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            matchRepository.delete(id);
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

    public  void createMatch(MatchDto matchDto){
        Session session = null;
        Transaction tx = null;
        Match match = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();

            match = new Match();
            match.setEpreuve(epreuveRepository.getById(matchDto.getEpreuve().getId()));
            match.setVainqueur(joueurRepository.getById(matchDto.getVainqueur().getId()));
            match.setFinaliste(joueurRepository.getById(matchDto.getFinaliste().getId()));
            Score score = new Score();
            score.setMatch(match);
            score.setSet1(matchDto.getScore().getSet1());
            score.setSet2(matchDto.getScore().getSet2());
            score.setSet3(matchDto.getScore().getSet3());
            score.setSet4(matchDto.getScore().getSet4());
            score.setSet5(matchDto.getScore().getSet5());
            match.setScore(score);

            matchRepository.create(match);
            //scoreRepository.create(match.getScore()); pas besoin si on annote la proriété score dans l'entité match avec la proptieté cascade=CascadeType.PERSIST
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

    public void tapisVert(Long id){
        Session session = null;
        Transaction tx = null;
        Match match = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();
            match = matchRepository.getById(id);

            Joueur ancienVainqueur = match.getVainqueur();
            match.setVainqueur(match.getFinaliste());
            match.setFinaliste(ancienVainqueur);

            match.getScore().setSet1((byte)0);
            match.getScore().setSet2((byte)0);
            match.getScore().setSet3((byte)0);
            match.getScore().setSet4((byte)0);
            match.getScore().setSet5((byte)0);

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

    public MatchDto getMatch(Long id){
        Session session = null;
        Transaction tx = null;
        MatchDto matchDto = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx =session.beginTransaction();

            Match match = matchRepository.getById(id);

            matchDto = new MatchDto();
            matchDto.setId(match.getId());

            JoueurDto vainqueurDto = new JoueurDto();
            vainqueurDto.setId(match.getVainqueur().getId());
            vainqueurDto.setNom(match.getVainqueur().getNom());
            vainqueurDto.setPrenom(match.getVainqueur().getPrenom());
            vainqueurDto.setSexe(match.getVainqueur().getSexe());
            matchDto.setVainqueur(vainqueurDto);

            JoueurDto finalisteDto = new JoueurDto();
            finalisteDto.setId(match.getFinaliste().getId());
            finalisteDto.setNom(match.getFinaliste().getNom());
            finalisteDto.setPrenom(match.getFinaliste().getPrenom());
            finalisteDto.setSexe(match.getFinaliste().getSexe());
            matchDto.setFinaliste(finalisteDto);

            EpreuveFullDto epreuveDto = new EpreuveFullDto();
            epreuveDto.setId(match.getEpreuve().getId());
            epreuveDto.setAnnee(match.getEpreuve().getAnnee());
            epreuveDto.setTypeEpreuve(match.getEpreuve().getTypeEpreuve());
            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(match.getEpreuve().getTournoi().getId());
            tournoiDto.setNom(match.getEpreuve().getTournoi().getNom());
            tournoiDto.setCode(match.getEpreuve().getTournoi().getCode());
            epreuveDto.setTournoi(tournoiDto);
            matchDto.setEpreuve(epreuveDto);

            ScoreFullDto scoreDto = new ScoreFullDto();
            scoreDto.setId(match.getScore().getId());
            scoreDto.setSet1(match.getScore().getSet1());
            scoreDto.setSet2(match.getScore().getSet2());
            scoreDto.setSet3(match.getScore().getSet3());
            scoreDto.setSet4(match.getScore().getSet4());
            scoreDto.setSet5(match.getScore().getSet5());
            matchDto.setScore(scoreDto);
            scoreDto.setMatch(matchDto);

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
        return matchDto;
    }
}
