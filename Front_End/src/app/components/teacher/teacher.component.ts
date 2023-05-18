import { Component, OnInit } from '@angular/core';
import { Teacher } from 'src/app/models/Teacher.model';
import { TeacherService } from '../../services/teacher/teacher.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Image } from 'src/app/models/Image.models';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.css']
})
export class TeacherComponent implements OnInit {
  public allTeachersInOnce: number[] = []
  public allTeachers: Teacher[] = [];
  public teachers: Teacher[] = [];
  public teacher: Teacher | undefined;
  public currentPage: number = 0;
  public isAdmin: Boolean = false;
  public uploadedImage!: File;
  public imagePath: any;
  public myImage!: string;
  public isImageUpdated: Boolean = false;
  public uploadApiServerUrl = `${environment.apiBaseUrl}/api/teachers`;

  constructor(private teacherService: TeacherService, private authService: AuthService) {  }
  ngOnInit(): void {
    this.getTeachers();
    this.isAdmin = this.authService.isAdmin();
  }

  public getTeacher(teacher: Teacher): void {
    this.teacher = teacher;
  }

  public getTeacherForUpdate(teacher: Teacher) {
    this.teacher = teacher;
    const observer = {
      next: (res: Image) => {
        this.myImage = `data:${res.imageType};base64,${res.image}`;
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }
    this.teacherService.loadImage(teacher.image.imageId).subscribe(observer);
  }

  // public getTeachers(): void {
  //   this.teacherService.getTeachers().subscribe((res: Teacher[]) => {
  //       this.allTeachers = res;
  //       this.teachers = this.allTeachers;
  //   })
  // }

  public getTeachers(): void {

    const observer = {
      next: (res: Teacher[]) => {
        this.allTeachers = res;
        this.teachers = this.allTeachers;

        this.allTeachers.forEach(teacher => {
          // this.teacherService.loadImage(teacher.image.imageId).subscribe({
          //   next: (res: Image) => {
          //     teacher.imageStr = `data:${res.imageType};base64,${res.image}`;
          //   },
          //   error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
          // })
          teacher.imageStr = `data:${teacher.images[0].imageType};base64,${teacher.images[0].image}`
        })
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    };
    this.teacherService.getTeachers().subscribe(observer);
  }

  public addTeacher(form: NgForm): void {
    form.value.role = 'teacher';
    const observer = {
      next: (res: Teacher) => {
        this.teacherService.uploadTeacherImage(
          this.uploadedImage,
          this.uploadedImage.name,
          res.teacherId
        ).subscribe((img: Image) => {
          let _teacher: Teacher = res;
          _teacher.imageStr = `data:${img.imageType};base64,${img.image}`;
          let _images: Image[] = []
          _images.push(img)
          _teacher.images = _images;
          this.allTeachers.push(_teacher);
          document.getElementById('addClose')?.click();
          this.imagePath = null;
          form.reset();
        });
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
    }

    // const imageObserver = {
    //   next: (res: Image) => {
    //     let teacher = {
    //       firstName: form.value.firstName,
    //       lastName: form.value.lastName,
    //       userName: form.value.userName,
    //       email: form.value.email,
    //       password: form.value.password,
    //       role: form.value.role,
    //       birthday: form.value.birthday,
    //     }
    //     this.teacherService.addTeacher(teacher, res.imageId).subscribe(observer);
    //   },
    //   error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    // }

    // this.teacherService.uploadImage(this.uploadedImage, this.uploadedImage.name).subscribe(imageObserver)
    this.teacherService.addTeacher(form.value).subscribe(observer);
  }

  public updateTeacher(teacher: Teacher): void {
    if (this.isImageUpdated) {
      const _teacher = {
        firstName: teacher.firstName,
        lastName: teacher.lastName,
        userName: teacher.userName,
        email: teacher.email,
        password: teacher.password,
        role: teacher.role,
        birthday: teacher.birthday,
      }

      this.teacherService.uploadImage(this.uploadedImage, this.uploadedImage.name).subscribe({
        next: (res: Image) => {
          this.teacherService.updateTeacher(teacher.teacherId, _teacher, res.imageId).subscribe({
            next: (res: Teacher) => {
              this.getTeachers();
              document.getElementById('updateClose')?.click();
            },
            error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
          }
        );
        },
        error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
      })
    } else {
      const _teacher = {
        firstName: teacher.firstName,
        lastName: teacher.lastName,
        userName: teacher.userName,
        email: teacher.email,
        password: teacher.password,
        role: teacher.role,
        birthday: teacher.birthday,
      }
      this.teacherService.updateTeacher(teacher.teacherId, _teacher, this.teacher?.image.imageId).subscribe(
        {
          next: (res: Teacher) => {
            this.getTeachers();
            document.getElementById('updateClose')?.click();
          },
          error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
        }
      );
    }
  }

  public deleteTeacher(teacherId: number): void {
    document.getElementById('deleteClose')?.click();

    const observer = {
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
      complete: () => {
        this.getTeachers();
      }
    }

    this.teacherService.deleteTeacher(teacherId).subscribe(observer);
  }


  public search(form: NgForm): void {
    let filteredTeachers = this.allTeachers.filter(
      teacher => teacher.userName.toLowerCase().includes(form.value.userName.toLowerCase())
    )
    this.teachers = filteredTeachers;
  }

  public onImageUpload(event: any, mode: string) {
    if (mode === 'new') {
      this.uploadedImage = event.target.files[0];
      var reader = new FileReader();
      reader.readAsDataURL(this.uploadedImage);
      reader.onload = (_event) => { this.imagePath = reader.result; }
    }

    if (mode === 'edit') {
      if(event.target.files && event.target.files.length) {
        this.uploadedImage = event.target.files[0];
        this.isImageUpdated = true;
        const reader = new FileReader();
        reader.readAsDataURL(this.uploadedImage);
        reader.onload = () => { this.myImage = reader.result as string; };
        }
    }
  }

  public onAddTeacherImage(): void {
    this.teacherService.uploadTeacherImage(
      this.uploadedImage,
      this.uploadedImage.name,
      this.teacher?.teacherId
    ).subscribe({
      next: (res: Image) => {
        this.teacher?.images.push(res);
        this.teacher!.imageStr = `data:${this.teacher!.images[0].imageType};base64,${this.teacher!.images[0].image}`
      },
      error: (error: HttpErrorResponse) => {}
    })
  }

  public deleteImage(image: Image): void {
      let conf = confirm("R u sure ?");
      if (conf)
        this.teacherService.deleteImage(image.imageId).subscribe(() => {
          const index = this.teacher!.images.indexOf(image, 0);

          if (index > -1) {
            this.teacher?.images.splice(index, 1);
            this.teacher!.imageStr = `data:${this.teacher!.images[0].imageType};base64,${this.teacher!.images[0].image}`
          }
    });
  }
}
