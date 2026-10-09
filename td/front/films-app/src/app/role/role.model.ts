import {Acteur} from '../acteur/acteur.model';

export interface Role {
  id: number;
  personnage: string;
  filmId: number;
  filmTitre: string;
  acteur: Acteur;
}
