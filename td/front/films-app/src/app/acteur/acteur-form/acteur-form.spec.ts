import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { ActeurForm } from './acteur-form';

describe('ActeurForm', () => {
  let component: ActeurForm;
  let fixture: ComponentFixture<ActeurForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActeurForm],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ActeurForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
