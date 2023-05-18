import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from 'src/app/models/Course.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiServerUrl = `${environment.apiBaseUrl}/api/courses`;

  constructor(private http: HttpClient,) { }

  public getCoursesPageByPage(page: number, size: number): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiServerUrl}?page=${page}&size=${size}`);
  }

  public getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiServerUrl}/all`);
  }

  public getCourse(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.apiServerUrl}/get/${courseId}`);
  }

  public addCourse(course: Course): Observable<Course> {
    const addedCourse = {
      courseName: course.courseName,
      courseDesc: course.courseDesc
    }
    return this.http.post<Course>(`${this.apiServerUrl}/create?teacher=${course.teacher}&speciality=${course.speciality}`, addedCourse);
  }

  public updateCourse(courseId: number, course: Course, oldCourse: Course | undefined): Observable<Course> {
    let query: string = `?teacher=${course.teacher}&speciality=${course.speciality}`;
    const updatedCourse = {
      courseName: course.courseName,
      courseDesc: course.courseDesc
    }

    if (`${course.teacher}` === '') {
      query = `?teacher=${oldCourse?.teacher.teacherId}&speciality=${course.speciality}`;
    }

    if (`${course.speciality}` === '') {
      query = `?teacher=${course.teacher}&speciality=${oldCourse?.speciality.specialityId}`;
    }

    if (`${course.speciality}` === '' && `${course.teacher}` === '') {
      query = `?teacher=${oldCourse?.teacher.teacherId}&speciality=${oldCourse?.speciality.specialityId}`;
    }

    console.log(updatedCourse);
    return this.http.patch<Course>(`${this.apiServerUrl}/update/${courseId}${query}`, updatedCourse);
  }

  public deleteCourse(courseId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/delete/${courseId}`);
  }
}
