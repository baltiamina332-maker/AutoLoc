package tn.esprit.autoloc.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}