import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  public user!: User;
  public wrongCredentials: string = '';
  public err: number = 0;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  public onLoggedIn(user: User) {
    this.user = user;
    this.authService.login(this.user).subscribe({
        next: (data) => {
          let jwToken = data.headers.get('Authorization')!;
          this.authService.saveToken(jwToken);
          this.router.navigate(['/']);
        },
        error: (err: any) => {
          this.err = 1;
        }
    })
  }

}
