import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'FotoHUb';
  isLogged!:boolean
  constructor(private authSrv: AuthService) {
    this.authSrv.loggedStatus.subscribe(status => this.isLogged = status);

  }
  ngOnInit(): void {
      this.authSrv.verifyLogin()
  }
  logout() {
    this.authSrv.logout()
  }
}
