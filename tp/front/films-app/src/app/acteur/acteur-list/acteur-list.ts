import { Component, inject } from "@angular/core";
import { AsyncPipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { ActeurService } from "../service/acteur-service";

@Component({
  selector: "app-acteur-list",
  imports: [AsyncPipe, RouterLink],
  templateUrl: "./acteur-list.html",
  styleUrl: "./acteur-list.css"
})
export class ActeurList {
  acteurs$ = inject(ActeurService).getAll();
}
