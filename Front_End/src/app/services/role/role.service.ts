import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from 'src/app/models/Roles.models';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private apiServerUrl = `${environment.apiBaseUrl}/api/roles`;

  constructor(private http: HttpClient) { }

  public getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiServerUrl}/all`);
  }

  public getRole(roleId: number): Observable<Role> {
    return this.http.get<Role>(`${this.apiServerUrl}/get/${roleId}`);
  }

  public addRole(role: Role): Observable<Role> {
    return this.http.post<Role>(`${this.apiServerUrl}/create`, role);
  }

  public updateRole(role: Role, roleId: number): Observable<Role> {
    return this.http.patch<Role>(`${this.apiServerUrl}/update/${roleId}`, role);
  }

  public deleteRole(roleId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${roleId}`);
  }
}
