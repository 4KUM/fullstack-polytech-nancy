import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Role } from "../role.model";

@Injectable({ providedIn: "root" })
export class RoleService {
  private http = inject(HttpClient);
  private url = "/api/roles";

  /*
Une autre manière d'écriture dans le service mais s'applique pas sur mon code.

private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
private url = `${environment.apiBaseUrl}/role`;
constructor(private httpClient: HttpClient) { }

 */

  getAll(): Observable<Role[]> {
    return this.http.get<Role[]>(this.url);
  }

  getById(id: number): Observable<Role> {
    return this.http.get<Role>(`${this.url}/${id}`);
  }

  getByFilm(filmId: number): Observable<Role[]> {
    return this.http.get<Role[]>(`/api/films/${filmId}/roles`);
  }

  getByActeur(acteurId: number): Observable<Role[]> {
    return this.http.get<Role[]>(`/api/acteurs/${acteurId}/roles`);
  }

  creer(filmId: number, role: { personnage: string; acteurId: number }): Observable<Role> {
    return this.http.post<Role>(`/api/films/${filmId}/roles`, role);
  }

  modifier(id: number, role: { personnage: string; acteurId: number }): Observable<Role> {
    return this.http.put<Role>(`${this.url}/${id}`, role);
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
