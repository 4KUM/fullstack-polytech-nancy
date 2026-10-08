# Développement Fullstack — Polytech

Bibliothèque de films : une API REST Spring Boot et un front Angular qui la consomme.

## Structure

    td/
      back/              API REST Spring Boot (films, acteurs, commentaires)
      front/films-app/   front Angular branché sur l'API
    tp/
      front/             vide

## Où trouver chaque TD

Les TD 1 et 2 ont été réalisés par erreur dans `tp/back` au lieu de `td/back`.
Le code a été déplacé dans `td/` pendant le TD 3, avec son historique.

| Tag   | Contenu                                   | Emplacement du code dans ce tag   |
|-------|-------------------------------------------|-----------------------------------|
| `TD1` | API REST, stockage en mémoire             | `tp/back`                         |
| `TD2` | persistance JPA, DTO                      | `tp/back`                         |
| `td3` | front Angular branché sur l'API           | `td/back` et `td/front/films-app` |

Pour consulter le TD 1 ou le TD 2 :

```bash
git checkout TD1   # ou TD2
cd tp/back
```

## Prérequis

- JDK 26 (le wrapper Gradle le télécharge si besoin)
- PostgreSQL, avec une base `Films` accessible par l'utilisateur `postgres` / mot de passe `0000`
  (modifiable dans `td/back/src/main/resources/application.yaml`)
- Node.js 24 LTS et Angular CLI : `npm install -g @angular/cli`

## Démarrer le back

```bash
cd td/back
./gradlew bootRun      # Windows : gradlew.bat bootRun
```

L'API écoute sur http://localhost:8080. Le schéma est recréé à chaque démarrage
(`ddl-auto: create-drop`) puis rempli avec quelques films, acteurs et commentaires
depuis `src/main/resources/data.sql`.

## Démarrer le front

```bash
cd td/front/films-app
npm install            # la première fois seulement
ng serve
```

L'application est servie sur http://localhost:4200.

## Brancher le front sur le back

Le front n'appelle jamais `localhost:8080` directement. Il appelle des URL relatives
en `/api/...`, que le serveur de développement Angular relaie vers l'API grâce au
proxy déclaré dans `src/proxy.conf.json` (et référencé dans `angular.json`) :

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "pathRewrite": { "^/api": "" }
  }
}
```

`/api/films` côté front devient donc `/films` côté Spring. Il suffit de démarrer le back,
puis le front : http://localhost:4200/api/films doit renvoyer du JSON.

## Fonctionnalités du front

- Liste des films, détail d'un film avec ses acteurs
- Création, modification et suppression d'un film
- Liste des acteurs, détail d'un acteur avec ses films
- Mise en évidence des films sortis avant 2000, du genre science-fiction et des films de Christopher Nolan
- Message d'erreur affiché si l'API ne répond pas

## Routes de l'API

| Méthode | URL                                   | Rôle                               |
|---------|---------------------------------------|------------------------------------|
| GET     | `/films`                              | liste des films                    |
| GET     | `/films/{id}`                         | détail d'un film                   |
| POST    | `/films`                              | création d'un film                 |
| PUT     | `/films/{id}`                         | modification d'un film             |
| DELETE  | `/films/{id}`                         | suppression d'un film              |
| GET     | `/films/{id}/acteurs`                 | acteurs d'un film                  |
| POST    | `/films/{id}/acteurs`                 | créer un acteur dans un film       |
| POST    | `/films/{id}/acteurs/{acteurId}`      | associer un acteur à un film       |
| DELETE  | `/films/{id}/acteurs/{acteurId}`      | dissocier un acteur d'un film      |
| GET     | `/acteurs`                            | liste des acteurs                  |
| GET     | `/acteurs/{id}`                       | détail d'un acteur                 |
| GET     | `/acteurs/{id}/films`                 | films d'un acteur                  |
| GET     | `/films/{id}/commentaires`            | commentaires d'un film             |
| POST    | `/films/{id}/commentaires`            | ajouter un commentaire             |
| PUT     | `/commentaires/{id}`                  | modifier un commentaire            |
| DELETE  | `/commentaires/{id}`                  | supprimer un commentaire           |
