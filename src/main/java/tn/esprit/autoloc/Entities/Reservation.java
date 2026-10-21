package tn.esprit.autoloc.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    // Plusieurs Reservations -> 1 Client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Plusieurs Reservations -> 1 Vehicule
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    // 1 Reservation <-> 1 Contrat
    @OneToOne
    @JoinColumn(name = "contrat_id", unique = true)
    private Contrat contrat;
}