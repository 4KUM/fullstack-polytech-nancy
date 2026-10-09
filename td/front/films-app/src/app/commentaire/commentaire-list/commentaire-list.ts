import { Component, inject, input, output, signal } from "@angular/core";
import { DatePipe } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { CommentaireService } from "../service/commentaire-service";
import { Commentaire } from "../commentaire.model";

@Component({
  selector: "app-commentaire-list",
  imports: [DatePipe, FormsModule],
  templateUrl: "./commentaire-list.html",
  styleUrl: "./commentaire-list.css"
})
export class CommentaireList {
  private service = inject(CommentaireService);

  filmId = input.required<number>();
  commentaires = input.required<Commentaire[]>();
  modifie = output<void>();

  auteur = signal("");
  message = signal("");
  erreur = signal("");

  ajouter() {
    const commentaire = { auteur: this.auteur(), message: this.message() };

    this.service.creer(this.filmId(), commentaire).subscribe({
      next: () => {
        this.auteur.set("");
        this.message.set("");
        this.erreur.set("");
        this.modifie.emit();
      },
      error: () => this.erreur.set("Impossible d'ajouter le commentaire")
    });
  }

  supprimer(c: Commentaire) {
    if (!confirm(`Supprimer le commentaire de ${c.auteur} ?`)) return;

    this.service.supprimer(c.id).subscribe({
      next: () => {
        this.erreur.set("");
        this.modifie.emit();
      },
      error: () => this.erreur.set("Impossible de supprimer le commentaire")
    });
  }
}
