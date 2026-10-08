import { Component, computed, inject, input, signal } from "@angular/core";
import { AsyncPipe, DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { toObservable } from "@angular/core/rxjs-interop";
import { Router, RouterLink } from "@angular/router";
import { catchError, of, switchMap } from "rxjs";
import { ActeurService } from "../service/acteur-service";
import { Acteur } from "../acteur.model";

@Component({
  selector: "app-acteur-detail",
  imports: [AsyncPipe, DatePipe, RouterLink],
  templateUrl: "./acteur-detail.html",
  styleUrl: "./acteur-detail.css"
})
export class ActeurDetail {
  private service = inject(ActeurService);
  private router = inject(Router);

  id = input.required<string>();
  acteurId = computed(() => Number(this.id()));
  erreur = signal("");

  acteur$ = toObservable(this.acteurId).pipe(
    switchMap(id => this.service.getById(id).pipe(
      catchError((e: HttpErrorResponse) => {
        this.erreur.set(e.status === 404 ? "Acteur introuvable" : "Erreur serveur");
        return of(null);
      })
    ))
  );

  films$ = toObservable(this.acteurId).pipe(
    switchMap(id => this.service.getFilms(id))
  );

  supprimer(acteur: Acteur) {
    if (!confirm(`Supprimer l'acteur « ${acteur.prenom} ${acteur.nom} » ?`)) {
      return;
    }
    this.service.supprimer(acteur.id).subscribe({
      next: () => this.router.navigate(["/acteurs"]),
      error: () => this.erreur.set("Impossible de supprimer cet acteur")
    });
  }
}
