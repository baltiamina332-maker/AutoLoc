package tn.esprit.autoloc.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false)
    private String nom;

    private String ville;

    private String adresse;

    private String telephone;

    // Associations (1 Agence -> N Vehicule, 1 Agence -> N Employe)
    // ajoutees a la Seance 3.
}
