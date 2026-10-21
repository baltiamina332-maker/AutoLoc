package tn.esprit.autoloc.Services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Entities.Contrat;
import tn.esprit.autoloc.Exception.ResourceNotFoundException;
import tn.esprit.autoloc.Repositories.IContratRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat entite) {
        if (entite.getIdContrat() != null) {
            throw new IllegalArgumentException("Nouveau Contrat : identifiant deja renseigne");
        }
        verifier(entite);
        return contratRepository.save(entite);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat entite) {
        Contrat existant = findById(id);
        verifier(entite);
        existant.setDateSignature(entite.getDateSignature());
        existant.setMontantTotal(entite.getMontantTotal());
        existant.setValide(entite.isValide());
        // Les associations et l'identifiant ne sont pas modifies ici.
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }


    private void verifier(Contrat entite) {
        if (entite.getMontantTotal() == null || entite.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Contrat : montantTotal ne peut pas etre negatif");
        }
    }
}
