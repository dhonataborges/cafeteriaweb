import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BebidaModel } from '../models/BebidaModel';
import { BebidaInput } from '../models/input/BebidaInput';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UsuarioModel } from '../models/UsuarioModel';

@Injectable({
  providedIn: 'root'
})

export class UsuarioService {

  apiUrl: String = environment.apiUrl;

  constructor(private http: HttpClient,
    private snack: MatSnackBar) { }

  // GET - lista todas os usuário
  findAll(): Observable<UsuarioModel[]> {
      const url = `${this.apiUrl}/usuarios`
      return this.http.get<UsuarioModel[]>(url);
  }

  // GET - Escolhe um usuário especifica pelo ID
  findById(id : number):Observable<UsuarioModel>{
    const url = `${this.apiUrl}/usuarios/${id}`;
    return this.http.get<UsuarioModel>(url);
  }

  // POST - Criar um novo usuário
  create(usuario: UsuarioModel):Observable<UsuarioModel> {
    const url = `${this.apiUrl}/usuarios`;
    return this.http.post<UsuarioModel>(url, usuario);
  }

  // PUT - Atualizar a usuário
  update(usuario: UsuarioModel):Observable<UsuarioModel> {
    const url = `${this.apiUrl}/usuarios/${usuario.id}`;
    return this.http.put<UsuarioModel>(url, usuario);
  }

    delete(id : number):Observable<void> {
    const url = this.apiUrl + `/usuarios/${id}`;
    return this.http.delete<void>(url);
  }

  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 6000,
      panelClass: ['custom-snackbar']
    })
  }

}
