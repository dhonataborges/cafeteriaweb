import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BebidaClassicaModel } from '../models/BebidaClassicaModel';
import { Observable } from 'rxjs';
import { BebidaClassicaInput} from '../models/input/BebidaClassicaInput';

@Injectable({
  providedIn: 'root'
})
export class BebidaClassicaService {

  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient,
    private snack: MatSnackBar) { }

  // GET - lista todas as bebidas
  findAll(): Observable<BebidaClassicaModel[]> {
      const url = `${this.apiUrl}/bebida-classica`
      return this.http.get<BebidaClassicaModel[]>(url);
  }

  // GET - Escolhe uma bebida especifica pelo ID
  findById(id : number):Observable<BebidaClassicaModel>{
    const url = `${this.apiUrl}/bebida-classica/${id}`;
    return this.http.get<BebidaClassicaModel>(url);
  }

  // POST - Criar uma nova bebida
  create(bebida: BebidaClassicaInput):Observable<BebidaClassicaInput> {
    const url = `${this.apiUrl}/bebida-classica`;
    return this.http.post<BebidaClassicaInput>(url, bebida);
  }

  // PUT - Atualizar a bebida
  update(bebida: BebidaClassicaInput):Observable<BebidaClassicaInput> {
    const url = `${this.apiUrl}/bebida-classica/${bebida.id}`;
    return this.http.put<BebidaClassicaInput>(url, bebida);
  }

    delete(id : number):Observable<void> {
    const url = this.apiUrl + `/bebida-classica/${id}`;
    return this.http.delete<void>(url);
  }

  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 4000,
      panelClass: ['custom-snackbar']
    })
  }

}
