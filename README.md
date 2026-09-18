# Développement Fullstack — Polytech

Dépôt de travail du cours. Il regroupe les TP du cours magistral et les TD à rendre.

## Structure

    tp/
      back/     projet Gradle + Spring Boot préconfiguré : TP Java / Spring
      front/    répertoire vide, destiné au projet créé par « ng new » : TP Angular
    td/
      back/     TD : API REST de la bibliothèque de films
        http/   requêtes HTTP, exécutées avec l'extension VSCode REST Client
      front/    TD : front Angular de la bibliothèque de films

L'ouverture du dossier racine dans VSCode déclenche la proposition des extensions
recommandées.

## Récupération du dépôt

```bash
git clone polytech-fullstack-starter.bundle mon-depot
cd mon-depot
git remote remove origin                 # le bundle ne constitue pas un dépôt distant
git remote add origin <URL du dépôt GitHub>
git push -u origin main
```

## Démarrage

### Back des TP

```bash
cd tp/back
./gradlew build      # Windows : gradlew.bat build
./gradlew bootRun
```

Le wrapper télécharge Gradle 9.7.1 et, le cas échéant, le JDK 26 : aucune installation
manuelle n'est nécessaire. Le fichier `build.gradle` ne déclare qu'une dépendance,
`spring-boot-starter-webmvc`. Elle apporte Spring MVC, Jackson, un Tomcat embarqué ainsi
que `spring-context`, le conteneur IoC utilisé dans les premiers TP.

### Front des TP

```bash
cd tp/front
ng new tp-front      # CSS, sans SSR, « None » pour les outils IA
```

### TD

Le back est généré depuis [start.spring.io](https://start.spring.io) ou depuis l'IDE,
**dans `td/back`**, avec la dépendance Spring Web. Le front est généré avec `ng new`,
**dans `td/front`**.

## Requêtes HTTP

Ni collection Postman ni collection Bruno : les requêtes sont versionnées dans des fichiers
`.http` placés dans `td/back/http` et exécutées par l'extension VSCode
[REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client),
via l'action *Send Request* affichée au-dessus de chaque requête. Un fichier par ressource,
requêtes séparées par `###`. `films.http` contient le squelette du TD 1.

## Rendus

| Tag   | Contenu                                        |
|-------|------------------------------------------------|
| `td1` | API REST, stockage en mémoire                  |
| `td2` | persistance JPA, DTO, CORS                     |
| `td3` | front Angular branché sur l'API                |

La régularité et la lisibilité des commits ainsi que la mise à jour du `README.md` sont
prises en compte dans l'évaluation.
