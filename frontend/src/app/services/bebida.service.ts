import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BebidaModel } from '../models/BebidaModel';
import { BebidaInput } from '../models/input/BebidaInput';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root' // Serviço disponível globalmente na aplicação
})
export class BebidaService {

  // URL base da API, configurada no environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  constructor(
    private http: HttpClient, // Cliente HTTP para chamadas REST
    private snack: MatSnackBar // Serviço para exibir mensagens ao usuário
  ) { }

  /**
   * Busca todas as bebidas cadastradas.
   * GET /bebida
   * @returns Observable que emite um array de BebidaModel
   */
  findAll(): Observable<BebidaModel[]> {
    const url = `${this.apiUrl}/bebida`;
    return this.http.get<BebidaModel[]>(url);
  }

  /**
   * Busca uma bebida específica pelo ID.
   * GET /bebida/{id}
   * @param id ID da bebida
   * @returns Observable que emite o BebidaModel correspondente
   */
  findById(id: number): Observable<BebidaModel> {
    const url = `${this.apiUrl}/bebida/${id}`;
    return this.http.get<BebidaModel>(url);
  }

  /**
   * Cria uma nova bebida (personalizada ou clássica).
   * POST /bebida
   * @param bebida Objeto BebidaInput com os dados da bebida
   * @returns Observable que emite o BebidaInput criado
   */
  create(bebida: BebidaInput): Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida`;
    return this.http.post<BebidaInput>(url, bebida);
  }

  /**
   * Verifica se uma bebida é clássica ou personalizada.
   * POST /bebida/verificar
   * @param bebida BebidaInput com ingredientes para verificação
   * @returns Observable que emite o resultado da verificação (pode conter nome da bebida clássica)
   */
  verificarBebida(bebida: BebidaInput): Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida/verificar`;
    return this.http.post<BebidaInput>(url, bebida);
  }

  /**
   * Atualiza uma bebida existente pelo ID.
   * PUT /bebida/{id}
   * @param bebida BebidaInput com dados atualizados (deve conter o id)
   * @returns Observable que emite o BebidaInput atualizado
   */
  update(bebida: BebidaInput): Observable<BebidaInput> {
    const url = `${this.apiUrl}/bebida/${bebida.id}`;
    return this.http.put<BebidaInput>(url, bebida);
  }

  /**
   * Exclui uma bebida pelo ID.
   * DELETE /bebida/{id}
   * @param id ID da bebida a ser removida
   * @returns Observable que emite void (sem conteúdo)
   */
  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/bebida/${id}`;
    return this.http.delete<void>(url);
  }

  /**
   * Exibe uma mensagem rápida para o usuário usando snackbar do Angular Material.
   * @param msg Texto da mensagem a ser exibida
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center', // Posiciona horizontalmente no centro
      verticalPosition: 'top',       // Posiciona verticalmente no topo
      duration: 6000,                // Duração da mensagem em milissegundos
      panelClass: ['custom-snackbar'] // Classe CSS customizada para estilo
    });
  }

}