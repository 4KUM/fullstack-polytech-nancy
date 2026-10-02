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
}
