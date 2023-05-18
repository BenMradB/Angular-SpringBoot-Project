import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Speciality } from 'src/app/models/Speciality.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SpecialityService } from 'src/app/services/speciality/speciality.service';

@Component({
  selector: 'app-speciality',
  templateUrl: './speciality.component.html',
  styleUrls: ['./speciality.component.css']
})
export class SpecialityComponent implements OnInit {
  public allSpecialitiesInOnce: number[] = [];
  public allSpecialities: Speciality[] = [];
  public specialities: Speciality[] = [];
  public speciality: Speciality | undefined;
  public currentPage: number = 0;
  public isAdmin: Boolean = false;


  constructor(private specialityService: SpecialityService, public authService: AuthService) {}

  ngOnInit(): void {
    this.getSpecialities();
    this.isAdmin = this.authService.isAdmin();

  }

  public getSpecialities(): void {
    const observer = {
      next: (res: Speciality[]) => {
        this.allSpecialities = res;
        this.specialities = this.allSpecialities;
        console.log(this.specialities);

      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.specialityService.getSpecialities().subscribe(observer);
  }

  public getSpeciality(speciality: Speciality) {
    this.speciality = speciality;
  }

  public addSpeciality(form: NgForm): void {
    const observer = {
      next: (res: Speciality) => {
        this.getSpecialities();
        form.reset();
        document.getElementById('addClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.specialityService.addSpeciality(form.value).subscribe(observer);
  }

  public updateSpeciality(speciality: Speciality): void {
    const observer = {
      next: (res: Speciality) => {
        this.getSpecialities();
        document.getElementById('updateClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    }

    this.specialityService.updateSpeciality(speciality.specialityId, speciality).subscribe(observer);
  }

  public deleteSpeciality(specialityId: number): void {
    const  observer = {
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
      complete: () => {
        this.getSpecialities();
        document.getElementById('deleteClose')?.click();
      }
    }

    this.specialityService.deleteSpeciality(specialityId).subscribe(observer);
  }

  public search(form: NgForm): void {
    let filteredSpecialities = this.allSpecialities.filter(
      speciality => speciality.specialityName.toLowerCase().includes(form.value.specialityName.toLowerCase())
    )
    this.specialities = filteredSpecialities;
  }

}
