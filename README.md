# AutoLoc — Gestion de location de véhicules

## 1. Présentation du projet

**AutoLoc** est une application de gestion de location de véhicules multi-agences, développée dans le cadre du module **Architecture des Systèmes d'Information (ASI)** à ESPRIT, année universitaire 2026–2027.

L'objectif est de mettre en place progressivement une application backend Java utilisant Spring Boot, Spring Data JPA et une base de données relationnelle MySQL.

## 2. Objectifs du projet

- Centraliser la gestion des agences de location.
- Gérer les véhicules, leurs catégories et leurs disponibilités.
- Gérer les clients et les employés.
- Permettre le suivi des réservations et des contrats de location.
- Gérer les paiements associés aux contrats.
- Assurer le suivi des maintenances des véhicules.
- Organiser l'application selon une architecture en couches.

## 3. Acteurs identifiés

| Acteur | Rôle |
|---|---|
| Client | Consulter les véhicules, effectuer des réservations et suivre ses locations |
| Agent d'agence | Gérer les réservations, les contrats et les opérations de location |
| Responsable d'agence | Superviser les véhicules, les employés et l'activité de son agence |
| Administrateur | Administrer l'ensemble des agences et les paramètres du système |

Ces cas d'utilisation constituent une première identification fonctionnelle du projet.

## 4. Environnement de développement

| Outil | Utilisation |
|---|---|
| Java JDK 17 | Développement Java |
| IntelliJ IDEA | Environnement de développement |
| Maven | Gestion des dépendances et compilation |
| Spring Boot | Framework backend |
| Spring Data JPA / Hibernate | Persistance des données |
| MySQL / MariaDB via XAMPP | Base de données |
| Lombok | Réduction du code répétitif |
| Git / GitHub | Gestion de versions |
| Postman | Tests des futures API REST |
| SonarQube for IDE | Analyse de la qualité du code |

## 5. Architecture du projet

L'application est organisée en plusieurs couches :

**Controller → Service → Repository → Base de données**

- **Entities :** modélisation des données.
- **Repositories :** accès à la base de données.
- **Services :** logique métier et validations.
- **RestController :** exposition des API REST lors des ateliers suivants.
- **Exception :** gestion des exceptions personnalisées.

## 6. Modèle de données

Le projet contient neuf entités principales :

1. Agence
2. Client
3. Employe
4. Vehicule
5. Equipement
6. Reservation
7. Contrat
8. Paiement
9. Maintenance

Les relations JPA permettent de représenter les associations entre les différentes entités du domaine.

## 7. Avancement des ateliers

| Atelier | Travail réalisé | État |
|---|---|---|
| Atelier 0 | Installation et configuration de l'environnement | Réalisé |
| Atelier 1 | Spring Boot, Maven, JPA et premières entités | Réalisé |
| Atelier 2 | Associations JPA, Fetch et Cascade | Réalisé |
| Atelier 3 | Repositories Spring Data JPA | Réalisé |
| Atelier 4 | IoC, injection de dépendances et couche Service | Implémenté, validation Git finale en attente |
| Atelier 5 | Contrôleurs REST et tests Postman | À venir |

## 8. Configuration et exécution

**Prérequis :** Java 17, Maven et un serveur MySQL/MariaDB opérationnel.

1. Ouvrir le projet AutoLoc dans IntelliJ IDEA.
2. Démarrer MySQL depuis XAMPP.
3. Vérifier la configuration de `src/main/resources/application.properties`.
4. Vérifier que la base `autoloc_db` est accessible.
5. Lancer la classe `AutolocApplication`.
6. Vérifier le démarrage de Spring Boot dans la console.

L'application utilise le port HTTP 8080 par défaut.

## 9. Vérification de l'environnement

Les vérifications réalisées comprennent :

- Détection des neuf interfaces JPA Repository.
- Connexion à la base de données.
- Initialisation d'Hibernate.
- Démarrage de Spring Boot et Tomcat.
- Analyse statique du code avec SonarQube for IDE.

Une capture d'écran du démarrage réussi de l'application est à conserver dans le dépôt Git.

## 10. Documentation

- `docs/repository-notes.md` : justification et analyse des repositories, Atelier 3.
- `docs/service-notes.md` : IoC, injection de dépendances, couche Service et SonarQube, Atelier 4.

---

**Module :** Architecture des Systèmes d'Information  
**Établissement :** ESPRIT  
**Année universitaire :** 2026–2027