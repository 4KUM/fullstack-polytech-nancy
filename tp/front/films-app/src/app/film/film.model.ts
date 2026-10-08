import { Acteur } from '../acteur/acteur.model';
import { Commentaire } from '../commentaire/commentaire.model';

export interface Film {
  id: number;
  titre: string;
  realisateur: string;
  dateSortie: string;
  genre: string;
  acteurs: Acteur[];
  commentaires: Commentaire[];
}
