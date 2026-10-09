import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RoleList } from './role-list';
import {provideHttpClientTesting} from '@angular/common/http/testing';
import {provideHttpClient} from '@angular/common/http';
import {provideRouter} from '@angular/router';

describe('RoleList', () => {
  let component: RoleList;
  let fixture: ComponentFixture<RoleList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleList],
      providers: [provideHttpClient(),provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(RoleList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
