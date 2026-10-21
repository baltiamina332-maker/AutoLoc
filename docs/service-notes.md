# Atelier 4 — IoC, Injection de Dépendances et Couche Service

**Projet :** AutoLoc  
**Module :** Architecture des Systèmes d'Information  
**Technologies :** Java 17, Spring Boot, Spring Data JPA, Lombok, MySQL, SonarQube for IDE

## A. Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Annotation utilisée | `@RequiredArgsConstructor` | `@Autowired` |
| Champ `final` possible | Oui | Non |
| Dépendances explicites | Oui, dans le constructeur | Non, cachées dans les attributs |
| Utilisation hors conteneur Spring | Oui, si la dépendance est fournie | Risque de `NullPointerException` |
| Testabilité | Facile | Plus difficile |
| Signalement SonarQube | Aucun problème | Règle S6813 |
| Recommandée | Oui | Non |

**Conclusion :**

L'injection par constructeur est préférable car elle garantit que les dépendances obligatoires sont fournies lors de la création de l'objet.

Dans notre projet AutoLoc, nous utilisons `@RequiredArgsConstructor` de Lombok pour générer automatiquement les constructeurs des attributs `final`.

Spring utilise ensuite ces constructeurs pour injecter les repositories.

## B. Services et dépendances injectées

| Service | Dépendance injectée | Mode d'injection | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Gestion des agences |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Gestion des employés |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Gestion des véhicules |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Gestion des équipements |
| ClientServiceImpl | IClientRepository | Constructeur | Gestion des clients |
| ReservationServiceImpl | IReservationRepository | Constructeur | Gestion des réservations |
| ContratServiceImpl | IContratRepository | Constructeur | Gestion des contrats |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Consultation des paiements uniquement |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Gestion des maintenances |

**Justification générale :**

Chaque service dépend d'une interface Repository et non d'une implémentation concrète. Cela favorise le couplage faible et facilite les tests et la maintenance.

Les classes d'implémentation utilisent `@Service` pour être détectées automatiquement par le conteneur Spring.

L'annotation `@RequiredArgsConstructor` permet l'injection par constructeur sans écrire manuellement le constructeur.

Les services sont sans état afin de pouvoir être partagés entre plusieurs requêtes.

**Règles métier implémentées :**

- Agence : refuser un nom vide.
- Employe : refuser un nom vide ou un rôle absent.
- Vehicule : refuser un tarif journalier négatif.
- Equipement : refuser un libellé vide.
- Client : refuser un nom ou un e-mail vide.
- Reservation : refuser une date de fin antérieure à la date de début.
- Contrat : refuser un montant total négatif.
- Paiement : autoriser uniquement `findById()` et `findAll()`.
- Maintenance : refuser une date de fin antérieure à la date de début.

## C. Diagnostic des messages d'erreur du conteneur Spring

### Message 1 — Bean introuvable

**Erreur :** `NoSuchBeanDefinitionException`

Spring ne trouve pas de bean de type `IContratService`.

**Causes possibles :**

1. L'annotation `@Service` manque sur `ContratServiceImpl`.
2. La classe est placée en dehors des packages analysés par Spring.

**Correction :**

Ajouter `@Service` sur `ContratServiceImpl` et vérifier que la classe appartient à un sous-package de `tn.esprit.autoloc`.

### Message 2 — Plusieurs beans du même type

**Erreur :** `NoUniqueBeanDefinitionException`

Spring trouve deux implémentations possibles : `emailNotificateur` et `smsNotificateur`.

**Correction 1 :** ajouter `@Primary` sur la classe de l'implémentation à sélectionner par défaut.

**Correction 2 :** utiliser `@Qualifier("emailNotificateur")` sur le paramètre du constructeur afin de sélectionner explicitement le bean voulu.

### Message 3 — Dépendance circulaire

**Erreur :** `BeanCurrentlyInCreationException`

`ClientServiceImpl` dépend de `ReservationServiceImpl`, et `ReservationServiceImpl` dépend de `ClientServiceImpl`.

**Cause :**

Les deux services ont besoin l'un de l'autre pour être créés. Avec l'injection par constructeur, chacun doit recevoir ses dépendances avant de pouvoir être instancié complètement.

**Correction :**

Revoir l'architecture pour supprimer la dépendance circulaire, par exemple en extrayant les traitements communs dans un troisième service indépendant.

## D. Anomalies SonarQube for IDE

Trois anomalies ont été observées durant les expériences de l'Atelier 4.

| Règle | Anomalie | Explication | Correction |
|---|---|---|---|
| S6813 | Injection par attribut | Les dépendances sont cachées et difficiles à tester | Injection par constructeur avec `@RequiredArgsConstructor` et un champ `final` |
| S112 | Exception générique | `RuntimeException` ne représente pas précisément l'erreur métier | Utiliser `ResourceNotFoundException` |
| S3655 | Accès non sécurisé à Optional | L'appel à `Optional.get()` peut échouer si aucune valeur n'est présente | Utiliser `orElseThrow()` |

**Expérience 1 :**

Nous avons temporairement remplacé l'injection par constructeur dans `ContratServiceImpl` par une injection utilisant `@Autowired` sur un attribut.

SonarQube a détecté la règle **S6813**. Nous avons ensuite restauré l'injection par constructeur.

**Expérience 2 :**

Nous avons temporairement remplacé `ResourceNotFoundException` par `RuntimeException`.

SonarQube a détecté la règle **S112**, signalant l'utilisation d'une exception générique.

**Expérience 3 :**

Nous avons utilisé `Optional.get()` pour récupérer un contrat.

SonarQube a détecté la règle **S3655**, signalant un accès potentiellement dangereux à une valeur optionnelle.

**Résultat final :**

Après restauration de `ResourceNotFoundException`, de `orElseThrow()` et de l'injection par constructeur, une nouvelle analyse de `ContratServiceImpl` n'a affiché aucune anomalie.

## E. Questions de compréhension

### 1. Où est le new de ContratServiceImpl et qui l'exécute ?

Le développeur n'écrit pas directement `new ContratServiceImpl(...)`.

C'est le conteneur Spring IoC qui crée et gère le bean `ContratServiceImpl` grâce à l'annotation `@Service`.

Spring fournit automatiquement son repository par injection de dépendances.

### 2. Pourquoi un contrôleur dépendra-t-il de IContratService et non de ContratServiceImpl ?

Parce que le contrôleur doit dépendre d'une abstraction et non d'une implémentation concrète.

Cela permet de remplacer l'implémentation du service sans modifier le contrôleur et facilite les tests unitaires.

Ce principe favorise le couplage faible.

### 3. Pourquoi un service singleton doit-il rester sans état ? Donner un exemple de bug.

Un service singleton possède une seule instance partagée entre plusieurs utilisateurs et requêtes.

S'il conserve des données modifiables propres à chaque requête, les utilisateurs risquent d'interférer entre eux.

**Exemple :** si `ContratServiceImpl` conserve un attribut `contratCourant`, une requête pourrait remplacer le contrat utilisé par une autre requête.

Les données temporaires doivent donc rester dans les variables locales des méthodes.

### 4. Pourquoi update charge-t-il l'entité existante avant d'appeler save ?

La méthode `update()` doit d'abord récupérer l'entité existante, modifier uniquement les attributs autorisés, puis appeler `save()`.

Cette approche préserve l'identifiant et les associations qui ne doivent pas être remplacées.

Enregistrer directement l'objet reçu pourrait écraser certaines données avec des valeurs nulles ou modifier involontairement des collections liées.

Dans AutoLoc, cela est particulièrement important pour les paiements d'un contrat, car l'association utilise `orphanRemoval = true`.

### 5. Différences entre les annotations et particularité de Paiement

**Différence entre `@Component` et `@Bean` :**

- `@Component` permet la détection automatique d'une classe comme bean Spring.
- `@Bean` permet de déclarer explicitement un bean à l'aide d'une méthode dans une classe de configuration.

**Différence entre `@Primary` et `@Qualifier` :**

- `@Primary` indique le bean à privilégier par défaut lorsque plusieurs implémentations existent.
- `@Qualifier` permet de choisir explicitement le bean à injecter.

**Pourquoi IPaiementService n'expose-t-il pas create, update et delete ?**

Parce que dans notre modèle AutoLoc, les paiements sont gérés à travers le contrat auquel ils appartiennent.

L'association Contrat–Paiement utilise `cascade = CascadeType.ALL` et `orphanRemoval = true`.

Le service Paiement est donc limité à la consultation avec `findById()` et `findAll()`.

---

## Conclusion

Cet atelier nous a permis de mettre en place la couche Service de l'application AutoLoc.

Nous avons appliqué l'Inversion de Contrôle et l'Injection de Dépendances pour relier les services aux repositories de manière faiblement couplée.

Les services centralisent désormais les opérations CRUD et les règles métier.

L'analyse SonarQube a permis d'identifier et de corriger plusieurs problèmes liés à l'injection et à la gestion des exceptions.

La couche Service est prête pour son utilisation depuis les contrôleurs REST lors de l'Atelier 5.