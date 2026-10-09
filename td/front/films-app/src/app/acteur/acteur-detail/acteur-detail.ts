import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { AsyncPipe, DatePipe } from "@angular/common";
import { HttpErrorResponse } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { toSignal } from "@angular/core/rxjs-interop";
import { Router, RouterLink } from "@angular/router";
import { Observable, catchError, map, of } from "rxjs";
import { ActeurService } from "../service/acteur-service";
import { Acteur } from "../acteur.model";
import { Film } from "../../film/film.model";
import { FilmService } from "../../film/service/film-service";
import { RoleService } from "../../role/service/role-service";
import { Role } from "../../role/role.model";

@Component({
  selector: "app-acteur-detail",
  imports: [AsyncPipe, DatePipe, FormsModule, RouterLink],
  templateUrl: "./acteur-detail.html",
  styleUrl: "./acteur-detail.css"
})
export class ActeurDetail implements OnInit {
  private service = inject(ActeurService);
  private router = inject(Router);
  private roleService = inject(RoleService);

  id = input.required<string>();
  acteurId = computed(() => Number(this.id()));
  erreur = signal("");

  acteur$!: Observable<Acteur | null>;
  films = signal<Film[] | null>(null);
  roles = signal<Role[]>([]);
  personnages = computed(() => new Map(this.roles().map(r => [r.filmId, r.personnage])));

  tousLesFilms = toSignal(
    inject(FilmService).getAll().pipe(
      map(films => [...films].sort((a, b) => a.titre.localeCompare(b.titre))),
      catchError(() => of([] as Film[]))
    ),
    { initialValue: [] }
  );
  filmSelectionne = signal<number | null>(null);
  personnage = signal("");
  message = signal("");
  roleExistant = computed(() => this.roles().find(r => r.filmId === this.filmSelectionne()));

  ngOnInit() {
    this.acteur$ = this.service.getById(this.acteurId()).pipe(
      catchError((e: HttpErrorResponse) => {
        this.erreur.set(e.status === 404 ? "Acteur introuvable" : "Erreur serveur");
        return of(null);
      })
    );
    this.recharger();
  }

  recharger() {
    this.service.getFilms(this.acteurId()).subscribe(f => this.films.set(f));
    this.roleService.getByActeur(this.acteurId()).subscribe({
      next: r => this.roles.set(r),
      error: () => this.roles.set([])
    });
  }

  choisirFilm(id: number | null) {
    this.filmSelectionne.set(id);
    this.personnage.set(this.roleExistant()?.personnage ?? "");
  }

  enregistrerRole() {
    const filmId = this.filmSelectionne();
    const personnage = this.personnage().trim();
    if (!filmId || !personnage) return;

    const role = { personnage, acteurId: this.acteurId() };
    const existant = this.roleExistant();
    const requete = existant
      ? this.roleService.modifier(existant.id, role)
      : this.roleService.creer(filmId, role);

    requete.subscribe({
      next: () => {
        this.filmSelectionne.set(null);
        this.personnage.set("");
        this.message.set("");
        this.recharger();
      },
      error: () => this.message.set("Impossible d'enregistrer ce rôle")
    });
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
