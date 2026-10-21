package tn.esprit.autoloc.Services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Entities.Equipement;
import tn.esprit.autoloc.Exception.ResourceNotFoundException;
import tn.esprit.autoloc.Repositories.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement entite) {
        if (entite.getIdEquipement() != null) {
            throw new IllegalArgumentException("Nouveau Equipement : identifiant deja renseigne");
        }
        verifier(entite);
        return equipementRepository.save(entite);
    }

    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement entite) {
        Equipement existant = findById(id);
        verifier(entite);
        existant.setLibelle(entite.getLibelle());
        // Les associations et l'identifiant ne sont pas modifies ici.
        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }


    private void verifier(Equipement entite) {
        if (entite.getLibelle() == null || entite.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Equipement : libelle obligatoire");
        }
    }
}
