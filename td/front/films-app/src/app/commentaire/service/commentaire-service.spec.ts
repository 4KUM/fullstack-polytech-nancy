import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { CommentaireService } from './commentaire-service';

describe('CommentaireService', () => {
  let service: CommentaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(CommentaireService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
