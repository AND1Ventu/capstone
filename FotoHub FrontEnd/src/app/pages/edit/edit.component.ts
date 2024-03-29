
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FotoService } from './../../services/foto.service';
import { Foto } from '../../Models/foto';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent {

  constructor(
    private route: ActivatedRoute,//per ottenere dati sulla rotta attiva
    private fotoSvc:FotoService
    ){}

  foto!:Foto;

  ngOnInit(){
    //usa questo per leggere il parametro id nell'url
    this.route.params.subscribe((params:any) => {

      this.fotoSvc.getById(params.id).then(res => this.foto = res)

    })

  }

  save(){
    this.fotoSvc.update(this.foto).then( res => {

    })
  }
}
