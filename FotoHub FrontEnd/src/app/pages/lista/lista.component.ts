import { Component } from '@angular/core';
import { Foto } from './../../Models/foto';
import { FotoService } from './../../services/foto.service';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.scss']
})
export class ListaComponent {
  fotos:Foto[] = [];

  constructor(private FotoSvc:FotoService){}

  ngOnInit(){
    this.FotoSvc.getAll().then(fotos => this.fotos = fotos)
  }

  delete(id:string|undefined){
    if(!id) return //blocca la funzione

    this.FotoSvc.delete(id).then(res => {

      this.fotos =  this.fotos.filter(f => f.id != id);

       alert(`Foto con id ${id} eliminata`)
    })
  }
}
