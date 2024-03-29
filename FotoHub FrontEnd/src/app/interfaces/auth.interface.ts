export interface Auth {
  email: string
  password: string
}

export interface SignUp {
  nome: string
  cognome: string
  password: string
  email: string
  bio: string
  id:number
}

export interface LoginData {
  token: any
  email: any
}
