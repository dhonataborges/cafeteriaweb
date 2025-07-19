import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment.ts/environment.prod';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredienteBaseModel } from '../models/IngredienteBaseModel';

@Injectable({
  providedIn: 'root' // Serviço disponível para toda a aplicação
})
export class IngredienteBaseService {

  // URL base da API definida no environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  constructor(
    private http: HttpClient, // Cliente HTTP para requisições REST
    private snack: MatSnackBar // Serviço para exibir mensagens rápidas (snackbars)
  ) { }

  /**
   * Busca todos os sabores base cadastrados.
   * GET /ingrediente-base
   * @returns Observable que emite uma lista de IngredienteBaseModel
   */
  findAll(): Observable<IngredienteBaseModel[]> {
    const url = `${this.apiUrl}/ingrediente-base`;
    return this.http.get<IngredienteBaseModel[]>(url);
  }

  /**
   * Busca um sabor base específico pelo ID.
   * GET /ingrediente-base/{id}
   * @param id ID do sabor base
   * @returns Observable que emite o IngredienteBaseModel correspondente
   */
  findById(id: number): Observable<IngredienteBaseModel> {
    const url = `${this.apiUrl}/ingrediente-base/${id}`;
    return this.http.get<IngredienteBaseModel>(url);
  }

  /**
   * Exibe uma mensagem rápida para o usuário usando snackbar do Angular Material.
   * @param msg Texto da mensagem a ser exibida
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center', // Centraliza a mensagem horizontalmente
      verticalPosition: 'top',       // Exibe a mensagem no topo da tela
      duration: 4000,                // Tempo de duração da mensagem em milissegundos
      panelClass: ['custom-snackbar'] // Classe CSS personalizada para estilo do snackbar
    });
  }
}