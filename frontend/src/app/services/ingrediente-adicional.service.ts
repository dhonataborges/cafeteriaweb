import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment.prod';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredienteAdicionalModel } from '../models/IngredienteAdicionalModel';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Serviço disponível em toda a aplicação
})
export class IngredienteAdicionalService {

  // URL base da API definida no environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  constructor(
    private http: HttpClient,  // Cliente HTTP para requisições REST
    private snack: MatSnackBar  // Serviço para mostrar mensagens (snackbars)
  ) { }

  /**
   * Busca todos os sabores adicionais cadastrados.
   * GET /ingrediente-adicional
   * @returns Observable que emite uma lista de IngredienteAdicionalModel
   */
  findAll(): Observable<IngredienteAdicionalModel[]> {
    const url = `${this.apiUrl}/ingrediente-adicional`;
    return this.http.get<IngredienteAdicionalModel[]>(url);
  }

  /**
   * Busca um sabor adicional específico pelo ID.
   * GET /ingrediente-adicional/{id}
   * @param id ID do sabor adicional
   * @returns Observable que emite o IngredienteAdicionalModel correspondente
   */
  findById(id: number): Observable<IngredienteAdicionalModel> {
    const url = `${this.apiUrl}/ingrediente-adicional/${id}`;
    return this.http.get<IngredienteAdicionalModel>(url);
  }

  /**
   * Exibe uma mensagem rápida para o usuário usando snackbar do Angular Material.
   * @param msg Texto da mensagem a ser exibida
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center', // Centraliza horizontalmente
      verticalPosition: 'top',       // Exibe no topo da tela
      duration: 4000,                // Duração em milissegundos
      panelClass: ['custom-snackbar'] // Classe CSS customizada para estilo
    });
  }

}