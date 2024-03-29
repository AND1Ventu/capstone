import { Injectable, OnDestroy } from '@angular/core';
import { Auth, LoginData, SignUp } from '../interfaces/auth.interface';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {
  // Auth
  private isLogged$ = new BehaviorSubject<boolean>(false);
  loggedStatus = this.isLogged$.asObservable();
  private onDestroy$ = new Subject<void>(); // Subject to emit when the service is destroyed

  jwtSrv = new JwtHelperService();

  private users$ = new BehaviorSubject<SignUp[]>([]);
  userList = this.users$.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  ngOnDestroy(): void {
    this.onDestroy$.next(); // Emit the completion signal when the service is destroyed
    this.onDestroy$.complete();
  }

  signup(formData: SignUp) {
    return this.http.post(`${environment.srvr}auth/register`, formData).pipe(
      tap((res) => {
        console.log(res);
      })
    );
  }

  login(cred: Auth) {
    return this.http.post<LoginData>(`${environment.srvr}auth/login`, cred).pipe(
      tap((res) => {
        // Store login data in local storage
        localStorage.setItem('email', JSON.stringify(res.email));
        localStorage.setItem('token', JSON.stringify(res.token));
        // Update logged status
        this.isLogged$.next(true);
      })
    );
  }



  private getLoginData(): LoginData | null {
    const email = localStorage.getItem('email');
    const token = localStorage.getItem('token');

    if (email && token) {
      return { email: JSON.parse(email), token: token };
    } else {
      return null;
    }
  }



  currentLoggedUsed(): Observable<SignUp[]> | undefined {
    const loginData = this.getLoginData();
    if (loginData) {

      const headers = new HttpHeaders({
      'Authorization': `Bearer ${loginData.token}`
      });

      return this.http
        .get<SignUp[]>(`${environment.srvr}logged-in-user`, { headers: headers })
        .pipe(takeUntil(this.onDestroy$)); // Unsubscribe when the service is destroyed
    } else {
      this.isLogged$.next(false);
      return;
    }
  }


  verifyLogin() {
    const data = this.getLoginData();
    if (data) {
      const isExpired = this.jwtSrv.isTokenExpired(data.token);
      this.isLogged$.next(!isExpired);
    } else {
      this.isLogged$.next(false);
    }
  }

  logout() {
    localStorage.removeItem('email');
    this.isLogged$.next(false);
    this.router.navigateByUrl('login');
  }

  // Public method to handle subscription cleanup
  unsubscribeOnDestroy(subscription: any): void {
    this.onDestroy$.pipe(takeUntil(this.onDestroy$)).subscribe(() => {
      subscription.unsubscribe();
    });
  }

  private getAllUsers() {}
}
