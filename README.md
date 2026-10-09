# Développement Fullstack — Polytech

> [!CAUTION]
> ## TD 1 et TD 2 : le code est dans le dossier `tp/`, pas dans `td/`
>
> **Je me suis trompé de dossier au début** : j'ai fait le TD 1 et le TD 2 dans `tp/back`
> au lieu de `td/back`. Le TD 3 est bien dans `td/` (`td/back` et `td/front/films-app`).
>
> - Pour le **TD 1** : `git checkout TD1`, puis ouvrir **`tp/back`**
> - Pour le **TD 2** : `git checkout TD2`, puis ouvrir **`tp/back`**
> - Pour le **TD 3** : `git checkout td3`, puis ouvrir **`td/back`** et **`td/front/films-app`**
>
> Le code du back a ensuite été déplacé de `tp/` vers `td/` pendant le TD 3, avec son historique.

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
- Création, modification et suppression d'un acteur
- Association d'un acteur existant à un film via un sélecteur, et dissociation
- Ajout et suppression de commentaires sur un film, depuis sa page de détail
- Mise en évidence des films sortis avant 2000, du genre science-fiction et des films de Christopher Nolan
- Message d'erreur affiché si l'API ne répond pas, message dédié pour une liste vide
- Page « introuvable » pour toute adresse inconnue

## Pages du front

| URL                     | Composant      | Rôle                                         |
|-------------------------|----------------|----------------------------------------------|
| `/films`                | `FilmList`     | liste des films                              |
| `/films/nouveau`        | `FilmForm`     | création d'un film                           |
| `/films/:id`            | `FilmDetail`   | détail d'un film, ses acteurs et commentaires |
| `/films/:id/modifier`   | `FilmForm`     | modification d'un film                       |
| `/acteurs`              | `ActeurList`   | liste des acteurs                            |
| `/acteurs/nouveau`      | `ActeurForm`   | création d'un acteur                         |
| `/acteurs/:id`          | `ActeurDetail` | détail d'un acteur et de ses films           |
| `/acteurs/:id/modifier` | `ActeurForm`   | modification d'un acteur                     |
| `/`                     |                | redirige vers `/films`                       |
| toute autre adresse     | `NotFound`     | page introuvable                             |

## Organisation du front

    src/app/
      film/
        film.model.ts          interface Film, calquée sur FilmDTO
        service/film-service   appels HTTP vers /api/films
        film-list/             liste des films
        film-card/             carte d'un film (input film, output supprimer)
        film-detail/           détail, suppression, association des acteurs
        film-form/             formulaire de création et d'édition
      acteur/
        acteur.model.ts        interface Acteur, calquée sur ActeurDTO
        service/acteur-service appels HTTP vers /api/acteurs
        acteur-list/  acteur-detail/  acteur-form/
      commentaire/
        commentaire.model.ts   interface Commentaire, calquée sur CommentaireDTO
        service/commentaire-service  appels HTTP pour ajouter et supprimer
        commentaire-list/      commentaires d'un film (input commentaires, output modifie)
      not-found/               page introuvable
      app.routes.ts            table de routage
      app.config.ts            router, HttpClient, locale française

Les appels HTTP passent uniquement par les services. Les lectures affichées sont
consommées avec `toSignal` ou le pipe `async`, les écritures avec `subscribe`.

## Tests

```bash
cd td/front/films-app
ng test
```

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
| POST    | `/acteurs`                            | création d'un acteur               |
| PUT     | `/acteurs/{id}`                       | modification d'un acteur           |
| DELETE  | `/acteurs/{id}`                       | suppression d'un acteur            |
| GET     | `/acteurs/{id}/films`                 | films d'un acteur                  |
| GET     | `/films/{id}/commentaires`            | commentaires d'un film             |
| POST    | `/films/{id}/commentaires`            | ajouter un commentaire             |
| PUT     | `/commentaires/{id}`                  | modifier un commentaire            |
| DELETE  | `/commentaires/{id}`                  | supprimer un commentaire           |
