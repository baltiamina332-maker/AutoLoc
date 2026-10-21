# Notes Atelier 4 : couche Service, IoC et DI

## A. Modes d'injection comparés

| Critère | Constructeur | Attribut (@Autowired) |
|---|---|---|
| Champ final possible | Oui | Non |
| Dépendance visible dans la signature de la classe | Oui | Non (cachée) |
| Utilisable avec new hors conteneur sans dépendance null | Oui (le constructeur impose la dépendance) | Non (le champ reste null) |
| Signalé par SonarQube for IDE | Non | Oui (règle S6813, à confirmer dans le panneau) |

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Champ final, dépendance typée par l'interface. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem. |
| ClientServiceImpl | IClientRepository | Constructeur | Idem. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem. |
| ContratServiceImpl | IContratRepository | Constructeur | Idem ; la suppression passe par le contexte de persistance (cascade sur les paiements). |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem ; lecture seule, les écritures passent par le Contrat. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem. |

## C. Messages d'erreur

**Message 1** (NoSuchBeanDefinitionException) : aucun bean de type IContratService. Causes probables : @Service oublié sur ContratServiceImpl, ou classe placée hors du package tn.esprit.autoloc (non détectée par le scan). Correction : ajouter @Service ou déplacer la classe.

**Message 2** (NoUniqueBeanDefinitionException) : deux beans du même type (emailNotificateur et smsNotificateur). Correction 1 : @Primary sur la classe du bean choisi par défaut (sur la classe). Correction 2 : @Qualifier("emailNotificateur") sur le paramètre du constructeur du contrôleur.

**Message 3** (dépendance circulaire) : clientServiceImpl et reservationServiceImpl s'injectent mutuellement. Avec l'injection par constructeur, chaque bean exige l'autre déjà créé : aucun ne peut être instancié en premier. Piste : extraire la responsabilité commune dans un troisième service dont les deux dépendent.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Injection par attribut (étape 2) | S6813 : l'injection par attribut cache les dépendances et empêche le champ final. | Retour à l'injection par constructeur avec @RequiredArgsConstructor. |
| RuntimeException lancée dans findById (étape 4) | S112 : une exception générique ne dit rien du problème. | Retour à ResourceNotFoundException. |
| à compléter | à compléter | à compléter |

## E. Questions de compréhension

1. Le new de ContratServiceImpl est exécuté par le conteneur Spring au démarrage : il détecte @Service lors du scan, puis appelle le constructeur en lui fournissant le proxy de IContratRepository.
2. Un contrôleur dépend de l'interface pour rester faiblement couplé : on peut changer d'implémentation sans modifier le contrôleur, et fournir une version de remplacement.
3. Un service est un singleton partagé par tous les threads. Exemple de bug : un attribut « contrat courant » écrasé par une requête concurrente fait qu'un utilisateur lit ou modifie le contrat d'un autre.
4. save avec un id fait un merge qui écraserait toutes les colonnes avec les valeurs reçues (y compris null) et, avec orphanRemoval, pourrait vider la collection de paiements. On charge donc l'existant et on ne recopie que les attributs modifiables.
5. @Component marque une classe détectée par le scan ; @Bean est posé sur une méthode d'une classe @Configuration pour déclarer un bean qu'on ne peut pas annoter (bibliothèque, JDK). @Primary désigne le candidat par défaut (sur la classe) ; @Qualifier choisit explicitement un bean à l'injection. IPaiementService est en lecture seule car le paiement fait partie du Contrat (composition) : on le crée et le retire via le Contrat, avec cascade et orphanRemoval.
