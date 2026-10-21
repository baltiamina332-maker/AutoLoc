package tn.esprit.autoloc.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // 1 Agence -> plusieurs Vehicules
    @OneToMany(mappedBy = "agence")
    private List<Vehicule> vehicules;

    // 1 Agence -> plusieurs Employes
    @OneToMany(mappedBy = "agence")
    private List<Employe> employes;
}