import { Injectable, inject } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, catchError, of } from "rxjs";
import { Acteur } from "../acteur.model";
import { Film } from "../../film/film.model";

@Injectable({ providedIn: "root" })
export class ActeurService {
  private http = inject(HttpClient);
  private url = "/api/acteurs";

  getAll(): Observable<Acteur[]> {
    return this.http.get<Acteur[]>(this.url);
  }

  getById(id: number): Observable<Acteur> {
    return this.http.get<Acteur>(`${this.url}/${id}`);
  }

  getFilms(id: number): Observable<Film[]> {
    return this.http.get<Film[]>(`${this.url}/${id}/films`).pipe(
      catchError((e: HttpErrorResponse) => {
        console.error(e.status, e.error?.detail);
        return of([]);
      })
    );
  }

  getByFilm(filmId: number): Observable<Acteur[]> {
    return this.http.get<Acteur[]>(`/api/films/${filmId}/acteurs`);
  }
}
