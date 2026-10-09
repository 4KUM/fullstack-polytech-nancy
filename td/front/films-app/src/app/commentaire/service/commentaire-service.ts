import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Commentaire } from "../commentaire.model";

@Injectable({ providedIn: "root" })
export class CommentaireService {
  private http = inject(HttpClient);

  creer(filmId: number, c: Partial<Commentaire>): Observable<Commentaire> {
    return this.http.post<Commentaire>(`/api/films/${filmId}/commentaires`, c);
  }

  supprimer(id: number): Observable<void> {
    return this.http.delete<void>(`/api/commentaires/${id}`);
  }
}
