package tn.esprit.autoloc.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}