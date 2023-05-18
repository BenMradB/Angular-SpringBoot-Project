import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Role } from 'src/app/models/Roles.models';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { RoleService } from 'src/app/services/role/role.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit{
  public allUsers: User[] = [];
  public users: User[] = [];
  public roles: Role[] = []
  public user?: User;
  public isAdmin: Boolean = false;

  public constructor(
    private userService: UserService,
    private roleService: RoleService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getUsers();
    this.getRoles();
    this.isAdmin = this.authService.isAdmin();
  }

  public getRoles(): void {
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        this.roles = roles;
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public getUsers(): void {
    this.userService.getUsers().subscribe({
      next: (users: User[]) => {
        this.allUsers = users;
        this.users = users;
        console.log(users);
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public getUser(user: User): void {
    this.user = user;
    // this.getRoles();
    // let _roles: Role[] = [];
    // user.roles.forEach(userRole => {
    //   _roles = this.roles.filter(role => role.role_id !== userRole.role_id);
    // });
    // this.roles = _roles;
  }

  public addUser(form: NgForm): void {
    this.userService.addUser(form.value).subscribe({
      next: (user: User) => {
        this.getUsers();
        form.reset();
        document.getElementById('addClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public grantRoleToUser(userId: number, form: NgForm): void {
    this.userService.grantRoleToUser(userId, form.value.role).subscribe({
      next: (user: User) => {
        this.user!.roles = user.roles;
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public updateUser(user: User): void {
    if (user.password === '') user.password = this.user!.password;
    user.roles = this.user!.roles;
    this.userService.updateUser(user, user.user_id).subscribe({
      next: (user: User) => {
        this.getUsers();
        document.getElementById('updateClose')?.click();
      },
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) }
    })
  }

  public deleteRole(user_id: number): void {
    this.userService.deleteUser(user_id).subscribe({
      error: (error: HttpErrorResponse) => { window.alert(`${error.message} 😢😢😢`) },
      complete: () => {
        this.getUsers();
        document.getElementById('deleteClose')?.click();
      }
    })
  }

  public search(form: NgForm): void {
    let filteredUsers = this.allUsers.filter(
      user => user.username.toLowerCase().includes(form.value.username.toLowerCase())
    )
    this.users = filteredUsers;
  }

  public revokeRoleFromUser(userId: number, role: Role): void {
        let conf = confirm("R u sure ?");
        if (conf) {
          this.userService.revokeRoleFromUser(userId, role.role_id).subscribe(() => {
            const index = this.user!.roles.indexOf(role, 0);

            if (index > -1) {
              this.user?.roles.splice(index, 1);
            }
          })
        }
  }

}
