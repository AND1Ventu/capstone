import { Pipe, PipeTransform } from '@angular/core';
import { SignUp } from '../interfaces/auth.interface';

@Pipe({
  name: 'fullName'
})
export class FullNamePipe implements PipeTransform {

  transform(value: SignUp):string {
    return value.nome + " " + value.cognome;
  }

}
