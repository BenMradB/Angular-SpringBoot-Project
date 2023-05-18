import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Speciality } from 'src/app/models/Speciality.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SpecialityService {
  private apiServerUrl = `${environment.apiBaseUrl}/api/specialities`;
  constructor(private http: HttpClient) { }

  public getSpecialities(): Observable<Speciality[]> {
    return this.http.get<Speciality[]>(`${this.apiServerUrl}/all`);
  }

  public getTeachersPageByPage(page: number, size: number): Observable<Speciality[]> {
    return this.http.get<Speciality[]>(`${this.apiServerUrl}?page=${page}&size=${size}`);
  }

  public addSpeciality(speciality: Speciality): Observable<Speciality> {
    return this.http.post<Speciality>(`${this.apiServerUrl}/create`, speciality);
  }

  public updateSpeciality(specialityId: number, speciality: Speciality): Observable<Speciality> {
    return this.http.patch<Speciality>(`${this.apiServerUrl}/update/${specialityId}`, speciality);
  }

  public deleteSpeciality(specialityId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${specialityId}`);
  }
}
