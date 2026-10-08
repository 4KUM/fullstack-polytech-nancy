import { Component, OnInit, computed, inject, input, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { ActeurService } from "../service/acteur-service";

@Component({
  selector: "app-acteur-form",
  imports: [FormsModule, RouterLink],
  templateUrl: "./acteur-form.html",
  styleUrl: "./acteur-form.css"
})
export class ActeurForm implements OnInit {
  private service = inject(ActeurService);
  private router = inject(Router);

  id = input<string>();
  acteurId = computed(() => Number(this.id()));
  enEdition = computed(() => !!this.id());

  prenom = signal("");
  nom = signal("");
  erreur = signal("");

  ngOnInit() {
    if (this.enEdition()) {
      this.service.getById(this.acteurId()).subscribe({
        next: a => {
          this.prenom.set(a.prenom);
          this.nom.set(a.nom);
        },
        error: () => this.erreur.set("Acteur introuvable")
      });
    }
  }

  enregistrer() {
    const acteur = { prenom: this.prenom(), nom: this.nom() };
    const requete = this.enEdition()
      ? this.service.modifier(this.acteurId(), acteur)
      : this.service.creer(acteur);

    requete.subscribe({
      next: a => this.router.navigate(["/acteurs", a.id]),
      error: () => this.erreur.set("Échec de l'enregistrement de l'acteur")
    });
  }
}
