import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Teacher } from '../../models/Teacher.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Image } from 'src/app/models/Image.models';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {
  private uploadApiServerUrl = `${environment.apiBaseUrl}/api/images`;
  private apiServerUrl = `${environment.apiBaseUrl}/api/teachers`;
  constructor(private http: HttpClient) { }

  public getTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(`${this.apiServerUrl}/all`);
  }

  public getTeachersPageByPage(page: number, size: number = 2): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(`${this.apiServerUrl}?page=${page}&size=${size}`);
  }

  public getTeacher(teacherId: number): Observable<Teacher> {
    return this.http.get<Teacher>(`${this.apiServerUrl}/get/${teacherId}`);
  }

  public addTeacher(teacher: any): Observable<Teacher> {
    return this.http.post<Teacher>(`${this.apiServerUrl}/create`, teacher);
  }

  // public addTeacher(teacher: any, imageId: number): Observable<Teacher> {
  //   return this.http.post<Teacher>(`${this.apiServerUrl}/create?imageId=${imageId}`, teacher);
  // }

  public updateTeacher(teacherId: number, teacher: any | undefined, imageId: number | undefined): Observable<Teacher> {
    return this.http.patch<Teacher>(`${this.apiServerUrl}/update/${teacherId}?imageId=${imageId}`, teacher);
  }

  public deleteTeacher(teacherId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${teacherId}`);
  }

  public uploadImage(file: File, filename: string): Observable<Image> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    return this.http.post<Image>(`${this.uploadApiServerUrl}/upload`, imageFormData);
  }

  public loadImage(id: number): Observable<Image> {
    return this.http.get<Image>(`${this.uploadApiServerUrl}/get/info/${id}`);
  }

  public uploadTeacherImage(file: File, filename: string, teacherId: number | undefined): Observable<Image> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    return this.http.post<Image>(
      `${this.uploadApiServerUrl}/uploadTeacherImage/${teacherId}`,
      imageFormData
    );
  }

  public getTeacherImages(teacherId: number | undefined): Observable<Image[]> {
    return this.http.get<Image[]>(`${this.uploadApiServerUrl}/getTeacherImages/${teacherId}`);
  }

  public deleteImage(imageId: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.uploadApiServerUrl}/delete/${imageId}`);
  }

  public uploadImageFS(file: File, filename: string, teacherId: number | undefined): Observable<void> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    return this.http.post<void>(`${this.uploadApiServerUrl}/uploadFS/${teacherId}`, imageFormData);
  }
}
