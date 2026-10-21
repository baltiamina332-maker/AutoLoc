package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.Entities.Paiement;
import java.util.List;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}
