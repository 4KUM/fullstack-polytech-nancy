import { Component, inject, signal } from "@angular/core";
import { AsyncPipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { catchError, of } from "rxjs";
import { ActeurService } from "../service/acteur-service";

@Component({
  selector: "app-acteur-list",
  imports: [AsyncPipe, RouterLink],
  templateUrl: "./acteur-list.html",
  styleUrl: "./acteur-list.css"
})
export class ActeurList {
  erreur = signal("");

  acteurs$ = inject(ActeurService).getAll().pipe(
    catchError(() => {
      this.erreur.set("Impossible de charger les acteurs : l'API ne répond pas.");
      return of(null);
    })
  );
}
