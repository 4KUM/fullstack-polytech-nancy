import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { ActeurDetail } from './acteur-detail';

describe('ActeurDetail', () => {
  let component: ActeurDetail;
  let fixture: ComponentFixture<ActeurDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActeurDetail],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(ActeurDetail);
    component = fixture.componentInstance;
    fixture.componentRef.setInput('id', '1');
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
