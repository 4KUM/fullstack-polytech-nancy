import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { FilmService } from './film-service';

describe('FilmService', () => {
  let service: FilmService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(FilmService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
