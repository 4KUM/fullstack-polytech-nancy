import {inject, Injectable, Service} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Role} from "../../role.model"

@Injectable({providedIn: 'root'})
export class RoleService {

  /*
  Une autre manière d'écriture dans le service mais s'applique pas sur mon code.

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private url = `${environment.apiBaseUrl}/role`;
  constructor(private httpClient: HttpClient) { }

   */
  private http = inject(HttpClient);
  private url = "/api/roles";

  getAll(): Observable<Role[]>{
    return this.http.get<Role[]>(this.url);
  }
}

