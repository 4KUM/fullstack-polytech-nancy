import { Routes } from "@angular/router";
import { FilmList } from "./film/film-list/film-list";
import { FilmDetail } from "./film/film-detail/film-detail";
import { ActeurList } from "./acteur/acteur-list/acteur-list";

export const routes: Routes = [
  { path: "films",     component: FilmList },
  { path: "films/:id", component: FilmDetail },
  { path: "acteurs",   component: ActeurList },
  { path: "",          redirectTo: "films", pathMatch: "full" },
];
