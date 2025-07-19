import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment.prod';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BebidaClassicaModel } from '../models/BebidaClassicaModel';
import { Observable } from 'rxjs';
import { BebidaClassicaInput } from '../models/input/BebidaClassicaInput';

@Injectable({
  providedIn: 'root' // Serviço disponível globalmente na aplicação
})
export class BebidaClassicaService {

  // URL base da API, configurada no arquivo environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  constructor(
    private http: HttpClient, // Cliente HTTP para fazer requisições REST
    private snack: MatSnackBar // Serviço para mostrar notificações (snackbars)
  ) { }

  /**
   * Busca todas as bebidas clássicas cadastradas.
   * GET /bebida-classica
   * @returns Observable que emite a lista de BebidaClassicaModel
   */
  findAll(): Observable<BebidaClassicaModel[]> {
    const url = `${this.apiUrl}/bebida-classica`;
    return this.http.get<BebidaClassicaModel[]>(url);
  }

  /**
   * Busca uma bebida clássica específica pelo seu ID.
   * GET /bebida-classica/{id}
   * @param id ID da bebida
   * @returns Observable que emite o BebidaClassicaModel correspondente
   */
  findById(id: number): Observable<BebidaClassicaModel> {
    const url = `${this.apiUrl}/bebida-classica/${id}`;
    return this.http.get<BebidaClassicaModel>(url);
  }

  /**
   * Cria uma nova bebida clássica.
   * POST /bebida-classica
   * @param bebida Objeto com os dados da bebida a ser criada
   * @returns Observable que emite o BebidaClassicaInput criado (pode ser ajustado para modelo correto)
   */
  create(bebida: BebidaClassicaInput): Observable<BebidaClassicaInput> {
    const url = `${this.apiUrl}/bebida-classica`;
    return this.http.post<BebidaClassicaInput>(url, bebida);
  }

  /**
   * Atualiza uma bebida clássica existente.
   * PUT /bebida-classica/{id}
   * @param bebida Objeto com os dados atualizados da bebida (deve conter o id)
   * @returns Observable que emite o BebidaClassicaInput atualizado
   */
  update(bebida: BebidaClassicaInput): Observable<BebidaClassicaInput> {
    const url = `${this.apiUrl}/bebida-classica/${bebida.id}`;
    return this.http.put<BebidaClassicaInput>(url, bebida);
  }

  /**
   * Exclui uma bebida clássica pelo seu ID.
   * DELETE /bebida-classica/{id}
   * @param id ID da bebida a ser excluída
   * @returns Observable que emite void (sem conteúdo)
   */
  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/bebida-classica/${id}`;
    return this.http.delete<void>(url);
  }

  /**
   * Exibe uma mensagem rápida para o usuário usando o snackbar do Angular Material.
   * @param msg Texto da mensagem a ser exibida
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center', // Posição horizontal do snackbar
      verticalPosition: 'top', // Posição vertical do snackbar
      duration: 4000, // Tempo que o snackbar fica visível (em milissegundos)
      panelClass: ['custom-snackbar'] // Classe CSS customizada para o estilo
    });
  }
}