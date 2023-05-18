import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Angular_SpringBoot_Project';
  constructor(private authService: AuthService, private router: Router) {}
  ngOnInit(): void {
    this.authService.loadToken();
    if (this.authService.getToken === null || this.authService.isTokenExpired()) {
      this.router.navigate(['/login'])
    }
  }
}
