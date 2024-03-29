import { Component, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnDestroy{
  email: string = "";
  password: string = "";
  loginSubscription: Subscription | undefined;

  constructor(
    private authSrv: AuthService,
    private router: Router
  ) { }


  submitLogin(form: NgForm) {
    this.loginSubscription = this.authSrv.login(form.value.credentials).subscribe({
      next: (res: any) => {
        console.log('Response Body:', res);
        if (res && res.token) {
          const token = res.token;
          localStorage.setItem('token', token);
          console.log('Token stored:', token);
          this.router.navigate(['']);
        } else {
          console.error('Token not found in response');
        }
      },
      error: (error: any) => {
        console.error('Error occurred:', error);
      }
    });
  }



  ngOnDestroy() {
    // Unsubscribe to avoid memory leaks
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
  }
}
