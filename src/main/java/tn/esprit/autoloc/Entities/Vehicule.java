package tn.esprit.autoloc.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVehicule;

    private String immatriculation;
    private String marque;
    private String model;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;


    // Plusieurs Vehicules -> 1 Agence
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;


    // 1 Vehicule -> plusieurs Reservations
    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations;


    // 1 Vehicule -> plusieurs Maintenances
    @OneToMany(mappedBy = "vehicule")
    private List<Maintenance> maintenances;


    // Plusieurs Vehicules <-> plusieurs Equipements
    @ManyToMany
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private List<Equipement> equipements;
}