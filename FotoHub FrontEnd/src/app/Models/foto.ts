import { iFoto } from './ifoto';

export class Foto implements iFoto {
  constructor(
    public nome: string,
    public valutazione: number,
    public fotografo: string,
    public active: boolean|string, public id?:string){}
}
