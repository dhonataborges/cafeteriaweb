import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredienteAdicionalModel } from '../models/IngredienteAdicionalModel';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class IngredienteAdicionalService {

  apiUrl: String = environment.apiUrl;

  constructor(private http: HttpClient,
    private snack: MatSnackBar) { }

  // GET - lista todas os sabores adicionais
  findAll(): Observable<IngredienteAdicionalModel[]> {
      const url = `${this.apiUrl}/ingrediente-adicional`
      return this.http.get<IngredienteAdicionalModel[]>(url);
  }

  // GET - Escolhe o sabor adicional especifico pelo ID
  findById(id : number):Observable<IngredienteAdicionalModel>{
    const url = `${this.apiUrl}/ingrediente-adicional/${id}`;
    return this.http.get<IngredienteAdicionalModel>(url);
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
