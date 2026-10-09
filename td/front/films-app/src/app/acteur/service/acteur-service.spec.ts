import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ActeurService } from './acteur-service';

describe('ActeurService', () => {
  let service: ActeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(ActeurService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
