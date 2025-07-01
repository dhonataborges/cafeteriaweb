import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BebidaModel } from '../models/BebidaModel';
import { BebidaInput } from '../models/input/BebidaInput';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class BebidaService {

  apiUrl: String = environment.apiUrl;

  constructor(private http: HttpClient,
    private snack: MatSnackBar) { }

  // GET - lista todas as bebidas
  findAll(): Observable<BebidaModel[]> {
      const url = `${this.apiUrl}/bebida`
      return this.http.get<BebidaModel[]>(url);
  }

  // GET - Escolhe uma bebida especifica pelo ID
  findById(id : number):Observable<BebidaModel>{
    const url = `${this.apiUrl}/bebida/${id}`;
    return this.http.get<BebidaModel>(url);
  }

  // POST - Criar uma nova bebida
  create(bebida: BebidaInput):Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida`;
    return this.http.post<BebidaInput>(url, bebida);
  }

   // POST - verificar bebida de é clássica ou personalizada
  verificarBebida(bebida: BebidaInput):Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida/verificar`;
    return this.http.post<BebidaInput>(url, bebida);
  }

  // PUT - Atualizar a bebida
  update(bebida: BebidaInput):Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida/${bebida.id}`;
    return this.http.put<BebidaInput>(url, bebida);
  }

    delete(id : number):Observable<void> {
    const url = this.apiUrl + `/bebida/${id}`;
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
