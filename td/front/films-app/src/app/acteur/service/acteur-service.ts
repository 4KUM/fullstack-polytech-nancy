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

  creer(a: Partial<Acteur>): Observable<Acteur> {
    return this.http.post<Acteur>(this.url, a);
  }

  modifier(id: number, a: Partial<Acteur>): Observable<Acteur> {
    return this.http.put<Acteur>(`${this.url}/${id}`, a);
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
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
