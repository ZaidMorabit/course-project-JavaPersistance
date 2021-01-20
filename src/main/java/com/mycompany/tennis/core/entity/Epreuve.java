package com.mycompany.tennis.core.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
@org.hibernate.annotations.NamedQuery(name = "given_sexe",query = "select e from Epreuve e join fetch e.tournoi where e.tournoi.code=?0")
@Entity
public class Epreuve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Type(type = "short") specifie le type via le type hibernate qui lie le type de la bdd et le type java voir doc hibernate
    private Short annee;
//    @Transient //dit à hibernate d'exclure la propriété de son mapping

    //many to one par rapport à l'entité meme
    // pour toute les relation ...toOne le fetch type par default est EAGER
    /* le fetch type lazy (ameliore les performances) va effectuer le join que si on veut acceder à l'entité à join (a condition d'avoir une session ouverte)
        deriere la propriété tournoi se cache un proxy qui va hériter de l'objet a acceder.
        il contient l'id de la 1ere table, l'objet au depart null, et des getter qui appelle le getter de l'objet
        si l'objet est null il va d'abord lire dans la right table pour instancier l'objet

     */
    @ManyToOne (fetch = FetchType.LAZY)
    //il s'agit de faire un join de  la table tournoi sur base de la colone "id_tournoi"
    //de base hibernate cherche la colonne entitée+_ID dans notre cas ce n'est pas cela
    @JoinColumn(name = "ID_TOURNOI")
    private Tournoi tournoi;
    @Column(name = "TYPE_EPREUVE") //specifie le nom dans la table de la bdd
    private Character typeEpreuve;

    @ManyToMany //Fetch type Lazy de base
    @JoinTable(
            name = "PARTICIPANTS",
            joinColumns = {@JoinColumn(name = "ID_EPREUVE")},
            inverseJoinColumns = {@JoinColumn(name = "ID_JOUEUR")})
    private Set<Joueur> participants;

    public Set<Joueur> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Joueur> participants) {
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getAnnee() {
        return annee;
    }

    public void setAnnee(Short annee) {
        this.annee = annee;
    }

    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    public Character getTypeEpreuve() {
        return typeEpreuve;
    }

    public void setTypeEpreuve(Character typeEpreuve) {
        this.typeEpreuve = typeEpreuve;
    }
}
