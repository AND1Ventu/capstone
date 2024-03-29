import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { ProfilePageGuard } from './components/profile-page/profile-page.guard';
import { SigninGuard } from './components/signin/signin.guard';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { Page404Component } from './pages/page404/page404.component';

const routes: Routes = [
  {
    path: "login",
    // component: SigninComponent,
    loadChildren: () => import("./components/signin/signin.module").then(m => m.SigninModule)
    , canActivate: [SigninGuard]
  },
  {
    path: "signup",
    // component: SignupComponent,
    loadChildren: () => import("./components/signup/signup.module").then(m => m.SignupModule)
  },
  {
    path: "",
    component: ProfilePageComponent,
    canActivate: [ProfilePageGuard],
  },
  {
    path: "users",
    component: AllUsersComponent
  },
  {
    path:'**',//è la pagina non found
    component: Page404Component
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
