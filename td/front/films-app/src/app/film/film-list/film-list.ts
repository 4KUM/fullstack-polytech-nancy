import { Component, computed, inject, signal } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { RouterLink } from "@angular/router";
import { catchError, of } from "rxjs";
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

  erreur = signal("");
  private idsSupprimes = signal<number[]>([]);

  private filmsCharges = toSignal(
    this.service.getAll().pipe(
      catchError(() => {
        this.erreur.set("Impossible de charger les films : l'API ne répond pas.");
        return of(null);
      })
    )
  );

  films = computed(() =>
    this.filmsCharges()?.filter(f => !this.idsSupprimes().includes(f.id))
  );

  onSupprimer(film: Film) {
    this.service.supprimer(film.id).subscribe({
      next: () => this.idsSupprimes.update(ids => [...ids, film.id]),
      error: () => this.erreur.set(`Impossible de supprimer le film « ${film.titre} »`)
    });
  }
}
