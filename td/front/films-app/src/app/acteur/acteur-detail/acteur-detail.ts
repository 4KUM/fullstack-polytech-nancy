import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { AsyncPipe, DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { Router, RouterLink } from "@angular/router";
import { Observable, catchError, of } from "rxjs";
import { ActeurService } from "../service/acteur-service";
import { Acteur } from "../acteur.model";
import { Film } from "../../film/film.model";

@Component({
  selector: "app-acteur-detail",
  imports: [AsyncPipe, DatePipe, RouterLink],
  templateUrl: "./acteur-detail.html",
  styleUrl: "./acteur-detail.css"
})
export class ActeurDetail implements OnInit {
  private service = inject(ActeurService);
  private router = inject(Router);

  id = input.required<string>();
  acteurId = computed(() => Number(this.id()));
  erreur = signal("");

  acteur$!: Observable<Acteur | null>;
  films$!: Observable<Film[]>;

  ngOnInit() {
    this.acteur$ = this.service.getById(this.acteurId()).pipe(
      catchError((e: HttpErrorResponse) => {
        this.erreur.set(e.status === 404 ? "Acteur introuvable" : "Erreur serveur");
        return of(null);
      })
    );
    this.films$ = this.service.getFilms(this.acteurId());
  }

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
