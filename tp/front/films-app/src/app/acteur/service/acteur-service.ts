import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Acteur } from "../acteur.model";

@Injectable({ providedIn: "root" })
export class ActeurService {
  private http = inject(HttpClient);
  private url = "/api/acteurs";

  getAll(): Observable<Acteur[]> {
    return this.http.get<Acteur[]>(this.url);
  }

  getByFilm(filmId: number): Observable<Acteur[]> {
    return this.http.get<Acteur[]>(`/api/films/${filmId}/acteurs`);
  }
}
