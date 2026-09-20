# AutoLoc

Plateforme de gestion de location de véhicules multi-agences.
Projet réalisé dans le cadre de l'UP ASI (Architecture des Systèmes d'Information) à ESPRIT.

## Objectifs du projet

- Gérer le parc de véhicules de plusieurs agences de location
- Permettre aux clients de consulter les véhicules disponibles et de les réserver
- Suivre les contrats de location, les retours de véhicules et la facturation
- Offrir aux responsables d'agence et à l'administrateur des outils de gestion et de suivi

## Acteurs identifiés

| Acteur | Rôle | Cas d'utilisation principaux |
|---|---|---|
| Client | Loue un véhicule | Consulter les véhicules, réserver, annuler une réservation, consulter son historique |
| Agent d'agence | Traite les locations au comptoir | Créer un contrat, enregistrer le retour d'un véhicule, encaisser |
| Responsable d'agence | Pilote son agence | Suivre le parc et les locations de son agence, consulter les statistiques |
| Administrateur | Administre la plateforme | Gérer les agences, les utilisateurs et les droits d'accès |

## Stack technique

Java 17+, Spring Boot, Spring Data JPA, Maven, MySQL, Postman, Git/GitHub, IntelliJ IDEA Ultimate.

## Équipe

- Amina Balti