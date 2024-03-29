import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from 'src/app/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class SigninGuard implements CanActivate {
  isLogged: boolean = false;
  constructor(private authSrv: AuthService, private router: Router) {
    this.authSrv.loggedStatus.subscribe(status => this.isLogged = !status);
  }
  canActivate(): boolean {
    this.authSrv.verifyLogin()
    if (!this.isLogged) {
      this.router.navigateByUrl("")
    }
    return this.isLogged;
  }

}
