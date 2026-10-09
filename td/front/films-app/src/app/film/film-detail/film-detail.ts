import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { toSignal } from "@angular/core/rxjs-interop";
import { Router, RouterLink } from "@angular/router";
import { catchError, of } from "rxjs";
import { FilmService } from "../service/film-service";
import { ActeurService } from "../../acteur/service/acteur-service";
import { Film } from "../film.model";
import { Acteur } from "../../acteur/acteur.model";

@Component({
  selector: "app-film-detail",
  imports: [DatePipe, FormsModule, RouterLink],
  templateUrl: "./film-detail.html",
  styleUrl: "./film-detail.css"
})
export class FilmDetail implements OnInit {
  private service = inject(FilmService);
  private router = inject(Router);

  id = input.required<string>();
  filmId = computed(() => Number(this.id()));

  film = signal<Film | null>(null);
  erreur = signal("");
  message = signal("");

  acteurs = toSignal(
    inject(ActeurService).getAll().pipe(catchError(() => of([] as Acteur[]))),
    { initialValue: [] }
  );
  acteurSelectionne = signal<number | null>(null);
  acteursDisponibles = computed(() =>
    this.acteurs().filter(a => !this.film()?.acteurs.some(fa => fa.id === a.id))
  );

  ngOnInit() {
    this.recharger();
  }

  recharger() {
    this.service.getById(this.filmId()).subscribe({
      next: f => this.film.set(f),
      error: (e: HttpErrorResponse) =>
        this.erreur.set(e.status === 404 ? "Film introuvable" : "Erreur serveur")
    });
  }

  associer() {
    const id = this.acteurSelectionne();
    if (!id) return;

    this.service.associerActeur(this.filmId(), id).subscribe({
      next: () => {
        this.acteurSelectionne.set(null);
        this.message.set("");
        this.recharger();
      },
      error: () => this.message.set("Association impossible")
    });
  }

  dissocier(acteur: Acteur) {
    this.service.dissocierActeur(this.filmId(), acteur.id).subscribe({
      next: () => {
        this.message.set("");
        this.recharger();
      },
      error: () => this.message.set("Dissociation impossible")
    });
  }

  supprimer(film: Film) {
    if (!confirm(`Supprimer le film « ${film.titre} » ?`)) return;

    this.service.supprimer(film.id).subscribe({
      next: () => this.router.navigate(["/films"]),
      error: () => this.message.set("Impossible de supprimer ce film")
    });
  }
}
