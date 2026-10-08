import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Film } from "../film.model";

@Injectable({ providedIn: "root" })
export class FilmService {
  private http = inject(HttpClient);
  private url = "/api/films";

  getAll(): Observable<Film[]> {
    return this.http.get<Film[]>(this.url);
  }

  getById(id: number): Observable<Film> {
    return this.http.get<Film>(`${this.url}/${id}`);
  }

  creer(f: Partial<Film>): Observable<Film> {
    return this.http.post<Film>(this.url, f);
  }

  modifier(id: number, f: Partial<Film>): Observable<Film> {
    return this.http.put<Film>(`${this.url}/${id}`, f);
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  associerActeur(id: number, acteurId: number): Observable<void> {
    return this.http.post<void>(`${this.url}/${id}/acteurs/${acteurId}`, null);
  }

  dissocierActeur(id: number, acteurId: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}/acteurs/${acteurId}`);
  }
}
