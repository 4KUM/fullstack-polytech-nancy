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
      back/              API REST Spring Boot (films, acteurs, rôles, commentaires)
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
(`ddl-auto: create-drop`) puis rempli depuis `src/main/resources/data.sql` avec :

- 57 films, avec le lien de leur affiche (images hébergées sur Wikipédia)
- les acteurs et leurs associations aux films
- un rôle (nom du personnage) pour chaque acteur associé à un film
- des commentaires

Les données ajoutées depuis le site sont donc perdues à chaque redémarrage du back.

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

- Liste des films avec leur affiche, détail d'un film avec son affiche et ses acteurs
- Création, modification et suppression d'un film, avec le lien de son affiche
  (aperçu de l'image dans le formulaire)
- Liste des acteurs, détail d'un acteur avec ses films
- Création, modification et suppression d'un acteur
- Association d'un acteur existant à un film avec le nom de son rôle (obligatoire) :
  une seule requête crée le rôle et l'association
- Dissociation d'un acteur : son rôle dans ce film est supprimé en même temps
- Ajout d'un rôle depuis la page d'un acteur (choix du film et du personnage) :
  l'acteur est ajouté au film s'il n'y est pas encore, et un rôle existant
  dans ce film est modifié au lieu d'être dupliqué
- Ajout et suppression de commentaires sur un film, depuis sa page de détail
- Liste des rôles (personnage, acteur, film)
- Nom du personnage affiché à côté de chaque acteur sur la page d'un film,
  et à côté de chaque film dans la filmographie d'un acteur
- Mise en évidence des films sortis avant 2000, du genre science-fiction et des films de Christopher Nolan
- Message d'erreur affiché si l'API ne répond pas, message dédié pour une liste vide
- Page « introuvable » pour toute adresse inconnue

## Thème et animations

Thème sombre rouge et noir, inspiré des plateformes de streaming :

- polices Bebas Neue (titres, logo) et Manrope (texte), chargées depuis Google Fonts
- fond animé : halos rouges qui dérivent, faisceau de projecteur, grain et vignette,
  et le mot « Cinéthèque » en 3D qui pivote lentement en perspective
- cartes et fiches en verre dépoli, légèrement transparentes
- animation d'ouverture (barre, logo avec reflet), entrées en cascade des cartes,
  survols et clics animés, transitions entre les pages
- barre de navigation fixe qui devient opaque au défilement

Les couleurs, polices et courbes d'animation sont des variables CSS dans `src/styles.css`.
Les animations sont coupées si le système demande de réduire les mouvements
(`prefers-reduced-motion`).

## Affiches des films

Le back stocke le lien de l'affiche dans la colonne `affiche` de la table `film`
(`@Column(length = 500)`, facultative). Le champ est présent dans `FilmDTO` et
`FilmCreationDTO`, et les liens des 57 films sont remplis par un `UPDATE` dans `data.sql`.

Côté front, l'affiche est :

- saisie dans le formulaire d'un film (champ « Lien de l'affiche », avec aperçu)
- affichée en haut de chaque carte de la liste, et à gauche des infos sur la page d'un film
- remplacée par un bloc sombre avec le titre si le film n'a pas d'affiche
  ou si le lien ne répond pas

Pour ajouter l'affiche d'un film existant, ajouter une ligne dans le `UPDATE film` de
`data.sql` : `('Titre exact du film', 'https://...')`.

## Pages du front

| URL                     | Composant      | Rôle                                         |
|-------------------------|----------------|----------------------------------------------|
| `/films`                | `FilmList`     | liste des films                              |
| `/films/nouveau`        | `FilmForm`     | création d'un film                           |
| `/films/:id`            | `FilmDetail`   | détail d'un film, son affiche, ses acteurs, leurs rôles et les commentaires |
| `/films/:id/modifier`   | `FilmForm`     | modification d'un film                       |
| `/acteurs`              | `ActeurList`   | liste des acteurs                            |
| `/acteurs/nouveau`      | `ActeurForm`   | création d'un acteur                         |
| `/acteurs/:id`          | `ActeurDetail` | détail d'un acteur, ses films, ses rôles et l'ajout d'un rôle |
| `/acteurs/:id/modifier` | `ActeurForm`   | modification d'un acteur                     |
| `/roles`                | `RoleList`     | liste des rôles                              |
| `/`                     |                | redirige vers `/films`                       |
| toute autre adresse     | `NotFound`     | page introuvable                             |

## Organisation du front

    src/app/
      film/
        film.model.ts          interface Film, calquée sur FilmDTO
        service/film-service   appels HTTP vers /api/films
        film-list/             liste des films
        film-card/             carte d'un film avec son affiche (input film, output supprimer)
        film-detail/           détail, affiche, suppression, association des acteurs avec leur rôle
        film-form/             formulaire de création et d'édition, lien de l'affiche
      acteur/
        acteur.model.ts        interface Acteur, calquée sur ActeurDTO
        service/acteur-service appels HTTP vers /api/acteurs
        acteur-list/  acteur-detail/  acteur-form/
      commentaire/
        commentaire.model.ts   interface Commentaire, calquée sur CommentaireDTO
        service/commentaire-service  appels HTTP pour ajouter et supprimer
        commentaire-list/      commentaires d'un film (input commentaires, output modifie)
      role/
        role.model.ts          interface Role, calquée sur RoleDTO
        service/role-service   appels HTTP vers /api/roles et les rôles d'un film ou d'un acteur
        role-list/             liste des rôles
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
| GET     | `/roles`                              | liste des rôles                    |
| GET     | `/roles/{id}`                         | détail d'un rôle                   |
| GET     | `/films/{id}/roles`                   | rôles d'un film                    |
| GET     | `/acteurs/{id}/roles`                 | rôles d'un acteur                  |
| POST    | `/films/{id}/roles`                   | créer un rôle (`personnage`, `acteurId`) |
| PUT     | `/roles/{id}`                         | modifier un rôle                   |
| DELETE  | `/roles/{id}`                         | supprimer un rôle                  |
