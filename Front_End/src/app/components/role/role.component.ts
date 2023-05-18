import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Role } from 'src/app/models/Roles.models';
import { AuthService } from 'src/app/services/auth/auth.service';
import { RoleService } from 'src/app/services/role/role.service';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent implements OnInit{

  public allRoles: Role[] = [];
  public roles: Role[] = [];
  public role?: Role;
  public isAdmin: Boolean = false;


  public constructor(private roleService: RoleService, public authService: AuthService) {}

  ngOnInit(): void {
    this.getRoles();
    this.isAdmin = this.authService.isAdmin();
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        this.allRoles = roles;
        this.roles = roles;
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public getRole(role: Role) {
    this.role = role;
  }

  public addRole(form: NgForm): void {
    this.roleService.addRole(form.value).subscribe({
      next: (role: Role) => {
        this.getRoles();
        form.reset();
        document.getElementById('addClose')?.click();
      },
      error: (error: HttpErrorResponse) => {}
    })
  }

  public updateRole(role: Role): void {
    this.roleService.updateRole(role, role.role_id).subscribe({
      next: (role: Role) => {
        this.getRoles();
        document.getElementById('updateClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public deleteRole(roleId: number): void {
    this.roleService.deleteRole(roleId).subscribe({
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
      complete: () => {
        this.getRoles();
        document.getElementById('deleteClose')?.click();

      }
    })
  }

  public search(form: NgForm): void {
    let filteredRoles = this.allRoles.filter(
      role => role.role.toLowerCase().includes(form.value.role.toLowerCase())
    )
    this.roles = filteredRoles;
  }

}
