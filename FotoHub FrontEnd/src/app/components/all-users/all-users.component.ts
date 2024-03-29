import { Component, OnInit } from '@angular/core';
import { SignUp } from 'src/app/interfaces/auth.interface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent {
  users!: SignUp[]
  constructor(private userSrv: AuthService) {
    this.userSrv.userList.subscribe(_users => {
      this.users = _users
    })
  }

}
