import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiServerUrl = `${environment.apiBaseUrl}/api/users`;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiServerUrl}/all`);
  }

  public getUser(userId: number): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/get/${userId}`);
  }

  public addUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/create`, user);
  }

  public grantRoleToUser(userId: number, roleId: number): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/grant/role/${roleId}/user/${userId}`, null);
  }

  public revokeRoleFromUser(userId: number, roleId: number): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/revoke/role/${roleId}/user/${userId}`, null);
  }

  public updateUser(user: User, userId: number): Observable<User> {
    return this.http.patch<User>(`${this.apiServerUrl}/update/${userId}`, user);
  }

  public deleteUser(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${userId}`);
  }
}
