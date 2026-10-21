package tn.esprit.autoloc.Services;

import tn.esprit.autoloc.Entities.Agence;
import java.util.List;

public interface IAgenceService {
    Agence create(Agence agence);
    Agence findById(Long id);
    List<Agence> findAll();
    Agence update(Long id, Agence agence);
    void deleteById(Long id);
}
