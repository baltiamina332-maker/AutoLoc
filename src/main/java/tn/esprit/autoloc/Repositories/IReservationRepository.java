package tn.esprit.autoloc.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}