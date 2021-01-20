package com.mycompany.tennis.core.service;

import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.dto.JoueurDto;
import com.mycompany.tennis.core.entity.Joueur;
import com.mycompany.tennis.core.repository.JoueurRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class JoueurService {

    private JoueurRepositoryImpl joueurRepository;

    public JoueurService() {
        this.joueurRepository = new JoueurRepositoryImpl();
    }

    public List<JoueurDto> getListJoueurs(char sexe){
        Session session = null;
        Transaction tx = null;
        List<JoueurDto> joueurDtos = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            List<Joueur> joueurs = joueurRepository.list(sexe);

            for(Joueur joueur : joueurs){
                final JoueurDto joueurDto = new JoueurDto();
                joueurDto.setId(joueur.getId());
                joueurDto.setNom(joueur.getNom());
                joueurDto.setPrenom(joueur.getPrenom());
                joueurDto.setSexe(joueur.getSexe());
                joueurDtos.add(joueurDto);
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
        return joueurDtos;
    }

    public void createJoueur(Joueur joueur){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueurRepository.create(joueur);
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

    }

    public Joueur getJoueur(long id){
        Session session = null;
        Transaction tx = null;
        Joueur joueur = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            joueur = joueurRepository.getById(id);
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
        return joueur;
    }

    public void renomme(Long id, String nouveauNom){ //ne doit pas gerer les interaction bdd mais on laisse ça ici pour l'instant

        //getJoueur(id) va recuperer l'objet en base de donnée et va ensuite fermer la session => l'objet n'est plus persistant, il est détaché
        Joueur joueur = getJoueur(id);

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();
            //Joueur joueur = joueurRepository.getById(id);
            joueur.setNom(nouveauNom);
            //↓ va dabord faire un select en bdd sur base de l'id de l'objet joueur et va modifier l'objet persistant sur base de l'objet détaché
            // merge renvoi l'objet persistant qu'il faut caster
            Joueur joueurPersistant = (Joueur) session.merge(joueur);
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
        //joueurRepository.renome(id,nouveauNom);
    }

    public void changeSexe(Long id, Character nouveauSexe){

        Joueur joueur = getJoueur(id);

        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            joueur.setSexe(nouveauSexe);

            session.merge(joueur);
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
    }

    public void deleteJoueur(Long id){
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tx = session.beginTransaction();

            joueurRepository.delete(id);
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
    }
}
