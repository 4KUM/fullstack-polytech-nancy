import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { toSignal } from "@angular/core/rxjs-interop";
import { Router, RouterLink } from "@angular/router";
import { catchError, of } from "rxjs";
import { FilmService } from "../service/film-service";
import { ActeurService } from "../../acteur/service/acteur-service";
import { Film, libelleGenre } from "../film.model";
import { Acteur } from "../../acteur/acteur.model";
import { CommentaireList } from "../../commentaire/commentaire-list/commentaire-list";
import { RoleService } from "../../role/service/role-service";
import { Role } from "../../role/role.model";

@Component({
  selector: "app-film-detail",
  imports: [DatePipe, FormsModule, RouterLink, CommentaireList],
  templateUrl: "./film-detail.html",
  styleUrl: "./film-detail.css"
})
export class FilmDetail implements OnInit {
  private service = inject(FilmService);
  private router = inject(Router);
  private roleService = inject(RoleService);

  id = input.required<string>();
  filmId = computed(() => Number(this.id()));

  film = signal<Film | null>(null);
  genre = computed(() => libelleGenre(this.film()?.genre ?? ""));
  erreur = signal("");
  message = signal("");

  roles = signal<Role[]>([]);
  personnages = computed(() => new Map(this.roles().map(r => [r.acteur.id, r.personnage])));

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
    this.roleService.getByFilm(this.filmId()).subscribe({
      next: r => this.roles.set(r),
      error: () => this.roles.set([])
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
