import { Component, computed, inject, input, signal } from "@angular/core";
import { AsyncPipe, DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { toObservable } from "@angular/core/rxjs-interop";
import { RouterLink } from "@angular/router";
import { catchError, of, switchMap } from "rxjs";
import { FilmService } from "../service/film-service";

@Component({
  selector: "app-film-detail",
  imports: [AsyncPipe, DatePipe, RouterLink],
  templateUrl: "./film-detail.html",
  styleUrl: "./film-detail.css"
})
export class FilmDetail {
  private service = inject(FilmService);

  id = input.required<string>();
  filmId = computed(() => Number(this.id()));
  erreur = signal("");

  film$ = toObservable(this.filmId).pipe(
    switchMap(id => this.service.getById(id).pipe(
      catchError((e: HttpErrorResponse) => {
        this.erreur.set(e.status === 404 ? "Film introuvable" : "Erreur serveur");
        return of(null);
      })
    ))
  );
}
