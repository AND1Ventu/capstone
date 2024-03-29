import { Injectable } from '@angular/core';
import { Foto } from '../Models/foto';

@Injectable({
  providedIn: 'root'
})
export class FotoService {

  constructor() { }

  apiUrl:string = 'http://localhost:3000/foto';

  getAll():Promise<Foto[]>{
    return fetch(this.apiUrl).then(res => res.json())
  }

  getById(id:string):Promise<Foto>{
    return fetch(this.apiUrl+`/${id}`).then(res => res.json())
  }

  create(Foto:Partial<Foto>):Promise<Foto>{
    Foto = this.toBoolean(Foto);
    return fetch(this.apiUrl,{
      method:'POST',
      headers:{
        'Content-Type':'application/json'
      },
      body:JSON.stringify(Foto)
    }).then(res => res.json())
  }

  update(foto:Foto):Promise<Foto>{
    foto = this.toBoolean(foto);
    return fetch(this.apiUrl+`/${foto.id}`,{
      method:'PUT',
      headers:{
        'Content-Type':'application/json'
      },
      body:JSON.stringify(foto)
    }).then(res => res.json())
  }

  delete(id:string):Promise<Foto>{
    return fetch(this.apiUrl+`/${id}`,{
      method:'DELETE',
      headers:{
        'Content-Type':'application/json'
      }
    }).then(res => res.json())
  }

  toBoolean<T>(foto:Partial<Foto>){
    foto.active = Boolean(Number(foto.active));
    return foto as T
  }

}
