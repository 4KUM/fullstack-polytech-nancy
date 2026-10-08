import { Component, inject } from "@angular/core";
import {AsyncPipe, DatePipe} from "@angular/common";
import { RouterLink } from "@angular/router";
import { FilmService } from "../service/film-service";

@Component({
  selector: "app-film-list",
  imports: [AsyncPipe, DatePipe, RouterLink],
  templateUrl: "./film-list.html",
  styleUrl: "./film-list.css"
})
export class FilmList {
  films$ = inject(FilmService).getAll();
}
