import { Component, inject } from "@angular/core";
import {AsyncPipe, DatePipe} from "@angular/common";
import { FilmService } from "../film/service/film-service";

@Component({
  selector: "app-film-list",
  imports: [AsyncPipe, DatePipe],
  templateUrl: "./film-list.html",
  styleUrl: "./film-list.css"
})
export class FilmList {
  films$ = inject(FilmService).getAll();
}
