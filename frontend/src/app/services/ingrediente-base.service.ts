import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment.ts/environment';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredienteBaseModel } from '../models/IngredienteBaseModel';

@Injectable({
  providedIn: 'root'
})
export class IngredienteBaseService {

  apiUrl: String = environment.apiUrl;

  constructor(private http: HttpClient,
    private snack: MatSnackBar) { }

  // GET - lista todas os sabores base
  findAll(): Observable<IngredienteBaseModel[]> {
      const url = `${this.apiUrl}/ingrediente-base`
      return this.http.get<IngredienteBaseModel[]>(url);
  }

  // GET - Escolhe o sabor base especifico pelo ID
  findById(id : number):Observable<IngredienteBaseModel>{
    const url = `${this.apiUrl}/ingrediente-base/${id}`;
    return this.http.get<IngredienteBaseModel>(url);
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