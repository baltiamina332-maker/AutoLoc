# Notes Atelier 3 : couche Repository

## Choix d'interface

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, findAll renvoie une List, tri, pagination et flush disponibles. |
| IEmployeRepository | JpaRepository<Employe, Long> | Idem : interface complète retenue pour toute l'application. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Idem. |
| IEquipementRepository | JpaRepository<Equipement, Long> | Idem. |
| IClientRepository | JpaRepository<Client, Long> | Idem. |
| IReservationRepository | JpaRepository<Reservation, Long> | Idem. |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Lecture des paiements ; création et suppression passent par le Contrat (composition). |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Idem. |

JpaRepository regroupe ListCrudRepository, ListPagingAndSortingRepository et les méthodes propres à JPA (flush, saveAndFlush, getReferenceById).
Attention : deleteAllInBatch et deleteAllByIdInBatch contournent le contexte de persistance, donc la cascade et l'orphanRemoval ne s'appliquent pas.

## Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| à compléter | à compléter | à compléter |
| à compléter | à compléter | à compléter |
| à compléter | à compléter | à compléter |
