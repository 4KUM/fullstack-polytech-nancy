import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { CommentaireList } from './commentaire-list';

describe('CommentaireList', () => {
  let component: CommentaireList;
  let fixture: ComponentFixture<CommentaireList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommentaireList],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(CommentaireList);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('filmId', 1);
    fixture.componentRef.setInput('commentaires', []);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
