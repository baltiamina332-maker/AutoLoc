package tn.esprit.autoloc.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.Entities.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}