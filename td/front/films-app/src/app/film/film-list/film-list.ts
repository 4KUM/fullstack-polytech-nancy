import { Component, inject, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { FilmService } from "../service/film-service";
import { FilmCard } from "../film-card/film-card";
import { Film } from "../film.model";

@Component({
  selector: "app-film-list",
  imports: [RouterLink, FilmCard],
  templateUrl: "./film-list.html",
  styleUrl: "./film-list.css"
})
export class FilmList {
  private service = inject(FilmService);

  films = signal<Film[] | null>(null);
  erreur = signal("");

  constructor() {
    this.service.getAll().subscribe({
      next: films => this.films.set(films),
      error: () => this.erreur.set("Impossible de charger les films : l'API ne répond pas.")
    });
  }

  onSupprimer(film: Film) {
    this.service.supprimer(film.id).subscribe({
      next: () => this.films.update(liste => liste!.filter(f => f.id !== film.id)),
      error: () => this.erreur.set(`Impossible de supprimer le film « ${film.titre} »`)
    });
  }
}
