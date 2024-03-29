import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms"
import { AuthService } from 'src/app/services/auth.service';
import { passwordMatch, passwordValidator } from 'src/app/utils/password.validator';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent {
  user: FormGroup = new FormGroup({
    nome: new FormControl("", [Validators.required]),
    cognome: new FormControl("", [Validators.required]),
    password: new FormControl("", [passwordMatch()]),
    passwordConf: new FormControl("", [passwordMatch()]),
    propic: new FormControl(""),
    email: new FormControl("", [Validators.required]),
    bio: new FormControl("", [Validators.minLength(100)]),
  }, {
    validators: passwordValidator
  })
  constructor(private authSrv: AuthService) { }

  submitForm(form: FormGroup) {
    delete form.value.passwordConf
    this.authSrv.signup(form.value).subscribe(res => console.log(res))
  }

}
