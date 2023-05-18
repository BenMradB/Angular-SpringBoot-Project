import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeacherComponent } from './components/teacher/teacher.component';
import CourseComponent from './components/course/course.component';
import { SpecialityComponent } from './components/speciality/speciality.component';
import { LoginComponent } from './components/login/login.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { TeacherGuard } from './guards/teacher.guard';
import { UserComponent } from './components/user/user.component';
import { RoleComponent } from './components/role/role.component';

const routes: Routes = [
  {
    path: '',
    component: TeacherComponent
  },
  {
    path: 'teachers',
    component: TeacherComponent
  },

  {
    path: 'courses',
    component: CourseComponent
  },

  {
    path: 'specialities',
    component: SpecialityComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'forbidden',
    component: ForbiddenComponent,
    canActivate: [TeacherGuard]
  },
  {
    path: 'users',
    component: UserComponent
  },
  {
    path: 'roles',
    component: RoleComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
