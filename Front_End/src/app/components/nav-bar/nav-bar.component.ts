import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit{
  public username: string = '';

  constructor(private router: Router, public authService: AuthService) {}
  ngOnInit(): void {
    this.authService.loadToken();
    if (this.authService.getToken === null || this.authService.isTokenExpired()) {
      this.username = "Connect";
      this.router.navigate(['/login'])
    } else {
      this.router.navigate(['/']);
    }
  }

  public logout(): void {
    this.authService.logout();
  }
}
