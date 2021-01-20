package com.mycompany.tennis.core.repository;

import com.mycompany.tennis.core.HibernateUtil;
import com.mycompany.tennis.core.entity.Epreuve;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EpreuveRepositoryImpl {
    public Epreuve getById(Long id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Epreuve epreuve = session.get(Epreuve.class, id);
        System.out.println("Epreuve lu");
        return epreuve;
    }

    public List<Epreuve> list(String codeTournoi){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        //en mettant join fetch e.tournoi on deproxifie directement l'object tournoi de epreuve meme si elle est en lazy loading
        Query<Epreuve> query = session.createNamedQuery("given_sexe",Epreuve.class);
        query.setParameter(0,codeTournoi);
        List<Epreuve> epreuves= query.getResultList();
        System.out.println("Epreuves lues");
        return epreuves;
    }
}
