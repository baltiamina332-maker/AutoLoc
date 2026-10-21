package tn.esprit.autoloc.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Vehicule;

public interface IVehiculeRepository
        extends JpaRepository<Vehicule, Long> {
}