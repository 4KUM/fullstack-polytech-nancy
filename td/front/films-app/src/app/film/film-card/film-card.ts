import { Component, computed, input, output, signal } from "@angular/core";
import { DatePipe, NgClass, NgStyle } from "@angular/common";
import { RouterLink } from "@angular/router";
import { Film, libelleGenre } from "../film.model";
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: "app-film-card",
  imports: [DatePipe, NgClass, NgStyle, RouterLink, ReactiveFormsModule],
  templateUrl: "./film-card.html",
  styleUrl: "./film-card.css"
})
export class FilmCard {
  film = input.required<Film>();
  supprimer = output<Film>();
  afficheCassee = signal(false);

  genre = computed(() => libelleGenre(this.film().genre));
  estAncien = computed(() => new Date(this.film().dateSortie).getFullYear() < 2000);
  estScienceFiction = computed(() => this.film().genre === "SCIENCE_FICTION");
  estDeNolan = computed(() => this.film().realisateur?.toLowerCase() === "christopher nolan");

  onSupprimer() {
    if (confirm(`Supprimer le film « ${this.film().titre} » ?`)) {
      this.supprimer.emit(this.film());
    }
  }
}
