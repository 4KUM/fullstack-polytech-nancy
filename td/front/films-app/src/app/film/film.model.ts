import { Acteur } from "../acteur/acteur.model";
import { Commentaire } from "../commentaire/commentaire.model";

export interface Film {
  id: number;
  titre: string;
  realisateur: string;
  dateSortie: string;
  genre: string;
  acteurs: Acteur[];
  commentaires: Commentaire[];
}

//Comme le front ne peux pas utiliser l'enum Genre j'ai ajouté ces genres pour le box drop down
export const GENRES = [
  { valeur: "ACTION", libelle: "Action" },
  { valeur: "AVENTURE", libelle: "Aventure" },
  { valeur: "ANIMATION", libelle: "Animation" },
  { valeur: "BIOPIC", libelle: "Biopic" },
  { valeur: "COMEDIE", libelle: "Comédie" },
  { valeur: "COMEDIE_DRAMATIQUE", libelle: "Comédie dramatique" },
  { valeur: "COMEDIE_MUSICALE", libelle: "Comédie musicale" },
  { valeur: "CRIME", libelle: "Crime" },
  { valeur: "DOCUMENTAIRE", libelle: "Documentaire" },
  { valeur: "DRAME", libelle: "Drame" },
  { valeur: "ESPIONNAGE", libelle: "Espionnage" },
  { valeur: "FAMILLE", libelle: "Famille" },
  { valeur: "FANTASTIQUE", libelle: "Fantastique" },
  { valeur: "GUERRE", libelle: "Guerre" },
  { valeur: "HISTORIQUE", libelle: "Historique" },
  { valeur: "HORREUR", libelle: "Horreur" },
  { valeur: "MUSICAL", libelle: "Musical" },
  { valeur: "MYSTERE", libelle: "Mystère" },
  { valeur: "POLICIER", libelle: "Policier" },
  { valeur: "ROMANCE", libelle: "Romance" },
  { valeur: "SCIENCE_FICTION", libelle: "Science-fiction" },
  { valeur: "SPORT", libelle: "Sport" },
  { valeur: "SUPER_HEROS", libelle: "Super-héros" },
  { valeur: "THRILLER", libelle: "Thriller" },
  { valeur: "WESTERN", libelle: "Western" },
];
