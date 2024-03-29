import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Foto } from '../../Models/foto';
import { FotoService } from './../../services/foto.service';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent {

  constructor(
    private fotoSvc:FotoService,
    private router:Router
    ){}

  newFoto:Partial<Foto> = {
    active:'0'
  };

  oldFoto:Foto|null = null;

  loading:boolean = false;

  save(){
    this.loading = true;

    this.fotoSvc.create(this.newFoto).then(res => {
      this.loading = false
      this.oldFoto = res;
      this.newFoto = {
        active:'0'
      }

      // setTimeout(()=>{
      //   this.router.navigate(['/'])
      // },3000)
    })
  }

}
