import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { TeacherComponent } from './components/teacher/teacher.component';
import CourseComponent from './components/course/course.component';
import { SpecialityComponent } from './components/speciality/speciality.component';
import { TeacherService } from './services/teacher/teacher.service';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { TokenInterceptor } from './services/interceptors/token.interceptor';
import { UserComponent } from './components/user/user.component';
import { RoleComponent } from './components/role/role.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    TeacherComponent,
    CourseComponent,
    SpecialityComponent,
    FooterComponent,
    LoginComponent,
    ForbiddenComponent,
    UserComponent,
    RoleComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    TeacherService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
