export interface Acteur {
  id: number;
  nom: string;
  prenom: string;
}

export interface Film {
  id: number;
  titre: string;
  realisateur: string;
  dateSortie: string;
  genre: string;
  acteurs: Acteur[];
}
