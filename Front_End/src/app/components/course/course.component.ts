import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Course } from 'src/app/models/Course.model';
import { Image } from 'src/app/models/Image.models';
import { Speciality } from 'src/app/models/Speciality.model';
import { Teacher } from 'src/app/models/Teacher.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CourseService } from 'src/app/services/course/course.service';
import { SpecialityService } from 'src/app/services/speciality/speciality.service';
import { TeacherService } from 'src/app/services/teacher/teacher.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export default class CourseComponent implements OnInit {
  public allCourses: Course[] = [];
  public courses: Course[] = [];
  public teachers: Teacher[] = [];
  public specialities: Speciality[] = [];
  public course: Course | undefined;
  public isAdmin: Boolean = false;

  constructor(
    private authService: AuthService,
    private courseService: CourseService,
    private teacherService: TeacherService,
    private specialityService: SpecialityService
  ) {}
  ngOnInit(): void {
    this.getCourses();
    this.getTeachers();
    this.getSpecialities();
    this.isAdmin = this.authService.isAdmin();
  }

  public getCourses(): void {
    const observer = {
      next: (res: Course[]) => {
        this.allCourses = res;
        this.courses = this.allCourses;
        this.courses.forEach(course => {
          this.teacherService.loadImage(course.teacher.images[0].imageId).subscribe({
            next: (res: Image) => {
              course.teacher.imageStr = `data:${res.imageType};base64,${res.image}`;
            },
            error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
          })
        });

      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.courseService.getCourses().subscribe(observer);
  }

  public getTeachers(): void {
    const observer = {
      next: (res: Teacher[]) => {
        this.teachers = res;
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    };
    this.teacherService.getTeachers().subscribe(observer);
  }

  public getSpecialities(): void {
    const observer = {
      next: (res: Speciality[]) => { this.specialities = res; },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.specialityService.getSpecialities().subscribe(observer);
  }

  public collectTeachersAndSpecialities(): void {
    this.getTeachers();
    this.getSpecialities();
  }

  public getCourse(course: Course, mode: string = 'delete'): void {
    this.course = course;
    if (mode === 'edit') {
      this.collectTeachersAndSpecialities();
    }
  }

  public addCourse(form: NgForm): void {
    const observer = {
      next: (res: Course) => {
        this.getCourses();
        form.reset();
        document.getElementById('addClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }


    this.courseService.addCourse(form.value).subscribe(observer);
  }

  public updateCourse(_course: Course): void {
    const observer = {
      next: (res: Course) => {
        this.getCourses();
        document.getElementById('editClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.courseService.updateCourse(_course.courseId, _course, this.course).subscribe(observer);
  }

  public deleteCourse(courseId: number): void {
    const observer = {
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
      complete: () => {
        this.courses = this.allCourses.filter(course => course.courseId !== courseId)
        document.getElementById('deleteClose')?.click();
      }
    }

    this.courseService.deleteCourse(courseId).subscribe(observer);
  }

  public search(form: NgForm): void {
    let filteredCourses = this.allCourses.filter(
      course => course.courseName.toLowerCase().includes(form.value.courseName.toLowerCase())
    )
    this.courses = filteredCourses;
  }
}
