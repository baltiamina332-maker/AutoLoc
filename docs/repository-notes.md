# Atelier 3 — Spring Data JPA et Repository

## 1. Choix des interfaces Repository

Les neuf interfaces Repository du projet AutoLoc héritent de `JpaRepository<Entité, Long>`.

| Interface | Hérite de | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet des agences |
| IClientRepository | JpaRepository<Client, Long> | Gestion des clients |
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, List, saveAndFlush |
| IEmployeRepository | JpaRepository<Employe, Long> | Gestion des employés |
| IEquipementRepository | JpaRepository<Equipement, Long> | Gestion des équipements |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Gestion des maintenances |
| IPaiementRepository | JpaRepository<Paiement, Long> | Consultation et gestion des paiements |
| IReservationRepository | JpaRepository<Reservation, Long> | Gestion des réservations |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD, tri et pagination des véhicules |

## 2. Analyse SonarQube for IDE

L'analyse du projet AutoLoc a été effectuée avec SonarQube for IDE.

- Fichiers analysés : 31
- Fichiers source Java analysés : 24
- Problèmes signalés : 0
- Security Hotspots : 0

Aucune anomalie n'a été signalée par SonarQube lors de cette analyse.

## 3. Amélioration du mapping JPA

Fichier : `Contrat.java`

La relation OneToMany entre Contrat et Paiement a été complétée par :

- `CascadeType.ALL` : propagation des opérations de persistance.
- `orphanRemoval = true` : suppression automatique des paiements orphelins.
- `mappedBy = "contrat"` : l'entité Paiement possède la relation.

Cette amélioration a été identifiée lors de la revue du mapping JPA ; elle ne correspond pas à une anomalie signalée par SonarQube.

## 4. Validation

- Neuf interfaces Repository créées.
- Démarrage Spring Boot réussi.
- Connexion à la base de données établie.
- Message obtenu : `Found 9 JPA repository interfaces`.
- Analyse statique SonarQube exécutée.