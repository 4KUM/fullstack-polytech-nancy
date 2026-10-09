import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { FilmCard } from './film-card';

describe('FilmCard', () => {
  let component: FilmCard;
  let fixture: ComponentFixture<FilmCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmCard],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(FilmCard);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('film', {
      id: 1, titre: 'Interstellar', realisateur: 'Christopher Nolan',
      dateSortie: '2014-11-05', genre: 'SCIENCE_FICTION', acteurs: [], commentaires: []
    });
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
