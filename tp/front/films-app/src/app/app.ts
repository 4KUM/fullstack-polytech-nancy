import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FilmList } from './film-list/film-list';

@Component({
  imports: [RouterOutlet, FilmList],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('films-app');
}
