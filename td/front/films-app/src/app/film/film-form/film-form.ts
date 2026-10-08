import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { FilmService } from "../service/film-service";
import { GENRES } from "../film.model";

@Component({
  selector: "app-film-form",
  imports: [FormsModule, RouterLink],
  templateUrl: "./film-form.html",
  styleUrl: "./film-form.css"
})
export class FilmForm implements OnInit {
  private service = inject(FilmService);
  private router = inject(Router);

  id = input<string>();
  filmId = computed(() => Number(this.id()));
  enEdition = computed(() => !!this.id());

  genres = GENRES;

  titre = signal("");
  realisateur = signal("");
  dateSortie = signal("");
  genre = signal("");
  erreur = signal("");

  ngOnInit() {
    if (this.enEdition()) {
      this.service.getById(this.filmId()).subscribe({
        next: f => {
          this.titre.set(f.titre);
          this.realisateur.set(f.realisateur);
          this.dateSortie.set(f.dateSortie);
          this.genre.set(f.genre);
        },
        error: () => this.erreur.set("Film introuvable")
      });
    }
  }

  enregistrer() {
    const film = {
      titre: this.titre(),
      realisateur: this.realisateur(),
      dateSortie: this.dateSortie(),
      genre: this.genre()
    };
    const requete = this.enEdition()
      ? this.service.modifier(this.filmId(), film)
      : this.service.creer(film);

    requete.subscribe({
      next: f => this.router.navigate(["/films", f.id]),
      error: () => this.erreur.set("Échec de l'enregistrement du film")
    });
  }
}
