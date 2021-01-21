package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.EntityManagerHolder;
import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.entity.Epreuve;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EpreuveRepositoryImpl {
    public Epreuve getById(Long id){
        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Epreuve epreuve = em.find(Epreuve.class, id);
        System.out.println("Epreuve lu");
        return epreuve;
    }

    public List<Epreuve> list(String codeTournoi){
        EntityManager em = EntityManagerHolder.getCurrentEntityManager();
        //Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //en mettant join fetch e.tournoi on deproxifie directement l'object tournoi de epreuve meme si elle est en lazy loading
        //Query<Epreuve> query = session.createNamedQuery("given_name",Epreuve.class);
        TypedQuery<Epreuve> query = em.createNamedQuery("given_name",Epreuve.class);
        query.setParameter(0,codeTournoi);
        List<Epreuve> epreuves= query.getResultList();
        System.out.println("Epreuves lues");
        return epreuves;
    }
}
