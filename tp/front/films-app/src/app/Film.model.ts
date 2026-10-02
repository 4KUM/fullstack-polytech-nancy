export type Genre =
  | 'ACTION' | 'AVENTURE' | 'ANIMATION' | 'BIOPIC' | 'COMEDIE'
  | 'COMEDIE_DRAMATIQUE' | 'COMEDIE_MUSICALE' | 'CRIME' | 'DOCUMENTAIRE'
  | 'DRAME' | 'ESPIONNAGE' | 'FAMILLE' | 'FANTASTIQUE' | 'GUERRE'
  | 'HISTORIQUE' | 'HORREUR' | 'MUSICAL' | 'MYSTERE' | 'POLICIER'
  | 'ROMANCE' | 'SCIENCE_FICTION' | 'SPORT' | 'SUPER_HEROS'
  | 'THRILLER' | 'WESTERN';

export interface Film {
  id: number;
  titre: string;
  realisateur: string | null;
  dateSortie: string | null;
  genre: Genre | null;
}
