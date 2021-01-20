package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.dto.EpreuveFullDto;
import com.mycompany.tennis.core.dto.EpreuveLightDto;
import com.mycompany.tennis.core.dto.JoueurDto;
import com.mycompany.tennis.core.dto.TournoiDto;
import com.mycompany.tennis.core.entity.Epreuve;
import com.mycompany.tennis.core.entity.Joueur;
import com.mycompany.tennis.core.repository.EpreuveRepositoryImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EpreuveService {

    private EpreuveRepositoryImpl epreuveRepository;

    public EpreuveService() {
        this.epreuveRepository= new EpreuveRepositoryImpl();
    }

    public List<EpreuveFullDto> getListJoueurs(String codeTournoi){
        Session session = null;
        Transaction tx = null;
        List<EpreuveFullDto> epreuveDtos = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            List<Epreuve> epreuves= epreuveRepository.list(codeTournoi);

            for(Epreuve epreuve : epreuves){
                final EpreuveFullDto epreuveDto= new EpreuveFullDto();
                epreuveDto.setId(epreuve.getId());
                epreuveDto.setAnnee(epreuve.getAnnee());
                epreuveDto.setTypeEpreuve(epreuve.getTypeEpreuve());
                TournoiDto tournoiDto = new TournoiDto();
                tournoiDto.setId(epreuve.getTournoi().getId());
                tournoiDto.setNom(epreuve.getTournoi().getNom());
                tournoiDto.setCode(epreuve.getTournoi().getCode());
                epreuveDto.setTournoi(tournoiDto);
                epreuveDtos.add(epreuveDto);
            }
            tx.commit();
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
        return epreuveDtos;
    }

    public EpreuveFullDto getEpreuveDetaillee(long id){
        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuveFullDto epreuveDto = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve =  epreuveRepository.getById(id);
            //Hibernate.initialize(epreuve.getTournoi()); // initialise le proxy tournoi dans epreuve

            epreuveDto = new EpreuveFullDto();
            epreuveDto.setId(epreuve.getId());
            epreuveDto.setAnnee(epreuve.getAnnee());
            epreuveDto.setTypeEpreuve(epreuve.getTypeEpreuve());
            TournoiDto tournoiDto = new TournoiDto();
            tournoiDto.setId(epreuve.getTournoi().getId());
            tournoiDto.setNom(epreuve.getTournoi().getNom());
            tournoiDto.setCode(epreuve.getTournoi().getCode());
            epreuveDto.setTournoi(tournoiDto);
            epreuveDto.setParticipants(new HashSet<>());

            for(Joueur joueur : epreuve.getParticipants()){
                JoueurDto joueurDto = new JoueurDto();
                joueurDto.setId(joueur.getId());
                joueurDto.setNom(joueur.getNom());
                joueurDto.setPrenom(joueur.getPrenom());
                joueurDto.setSexe(joueur.getSexe());
                epreuveDto.getParticipants().add(joueurDto);
        }

            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }
        return epreuveDto;
    }

    public EpreuveLightDto getEpreuveSansTournoi(long id){
        Session session = null;
        Transaction tx = null;
        Epreuve epreuve = null;
        EpreuveLightDto dto = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            epreuve =  epreuveRepository.getById(id);

            dto = new EpreuveLightDto();
            dto.setId(epreuve.getId());
            dto.setAnnee(epreuve.getAnnee());
            dto.setTypeEpreuve(epreuve.getTypeEpreuve());
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session != null){
                session.close();
            }
        }
        return dto;
    }



}
