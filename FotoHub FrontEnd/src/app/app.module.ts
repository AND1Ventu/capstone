import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './components/signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SigninComponent } from './components/signin/signin.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
//import { HttpErrorInterceptor } from './http-error.interceptor';
//import { HttpErrorHandlerService } from './services/http-error-handler.service';
import { FullNamePipe } from './pipes/full-name.pipe';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { CreateComponent } from './pages/create/create.component';
import { EditComponent } from './pages/edit/edit.component';
import { ListaComponent } from './pages/lista/lista.component';
import { ManagementComponent } from './pages/management/management.component';
import { Page404Component } from './pages/page404/page404.component';
import { GalleryComponent } from './pages/gallery/gallery.component';

@NgModule({
  declarations: [
    AppComponent,
    ProfilePageComponent,
    FullNamePipe,
    AllUsersComponent,
    CreateComponent,
    EditComponent,
    ListaComponent,
    ManagementComponent,
    Page404Component,
    GalleryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
