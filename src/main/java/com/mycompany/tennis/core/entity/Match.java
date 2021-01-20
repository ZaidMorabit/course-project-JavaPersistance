package com.mycompany.tennis.core.entity;


import javax.persistence.*;

@Entity
@Table(name = "match_tennis")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VAINQUEUR")
    private Joueur vainqueur;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FINALISTE")
    private Joueur finaliste;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EPREUVE")
    private Epreuve epreuve;
    //si le cascade type mis avec la valeur persist il quand on persiste un match il va persister le score aussi
    @OneToOne(mappedBy = "match", fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval = true) //relation bidirectionnel dont le mapping a deja été fait dans l'entité score
    private Score score;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Joueur getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(Joueur vainqueur) {
        this.vainqueur = vainqueur;
    }

    public Joueur getFinaliste() {
        return finaliste;
    }

    public void setFinaliste(Joueur finaliste) {
        this.finaliste = finaliste;
    }

    public Epreuve getEpreuve() {
        return epreuve;
    }

    public void setEpreuve(Epreuve epreuve) {
        this.epreuve = epreuve;
    }
}
