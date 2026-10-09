import {Component, inject, signal} from '@angular/core';
import {AsyncPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {RoleService} from './service/role-service';
import {catchError, of} from 'rxjs';

@Component({
  imports: [AsyncPipe, RouterLink],
  selector: 'app-role-list',
  styleUrl: './role-list.css',
  templateUrl: './role-list.html',
})
export class RoleList {
  erreur = signal("");

  roles$ = inject(RoleService).getAll().pipe(
    catchError(()=>{
      this.erreur.set("Impossible de charger les roles: l'API ne répond pas. ");
      return of(null);
    })
  )
}
