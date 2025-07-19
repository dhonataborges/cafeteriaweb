import { Injectable } from '@angular/core';
import { environment } from '../environment.ts/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioModel } from '../models/UsuarioModel';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root' // Serviço disponível em toda a aplicação
})
export class UsuarioService {

  // URL base da API definida no environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  constructor(
    private http: HttpClient,  // Cliente HTTP para chamadas REST
    private snack: MatSnackBar // Serviço para mostrar mensagens rápidas (snackbars)
  ) { }

  /**
   * Busca todos os usuários cadastrados.
   * GET /usuarios
   * @returns Observable que emite uma lista de UsuarioModel
   */
  findAll(): Observable<UsuarioModel[]> {
    const url = `${this.apiUrl}/usuarios`;
    return this.http.get<UsuarioModel[]>(url);
  }

  /**
   * Busca um usuário específico pelo ID.
   * GET /usuarios/{id}
   * @param id ID do usuário
   * @returns Observable que emite o UsuarioModel correspondente
   */
  findById(id: number): Observable<UsuarioModel> {
    const url = `${this.apiUrl}/usuarios/${id}`;
    return this.http.get<UsuarioModel>(url);
  }

  /**
   * Cria um novo usuário.
   * POST /usuarios
   * @param usuario Dados do usuário a ser criado
   * @returns Observable que emite o usuário criado
   */
  create(usuario: UsuarioModel): Observable<UsuarioModel> {
    const url = `${this.apiUrl}/usuarios`;
    return this.http.post<UsuarioModel>(url, usuario);
  }

  /**
   * Atualiza um usuário existente.
   * PUT /usuarios/{id}
   * @param usuario Dados atualizados do usuário (deve conter o id)
   * @returns Observable que emite o usuário atualizado
   */
  update(usuario: UsuarioModel): Observable<UsuarioModel> {
    const url = `${this.apiUrl}/usuarios/${usuario.id}`;
    return this.http.put<UsuarioModel>(url, usuario);
  }

  /**
   * Deleta um usuário pelo ID.
   * DELETE /usuarios/{id}
   * @param id ID do usuário a ser removido
   * @returns Observable que emite void após remoção
   */
  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/usuarios/${id}`;
    return this.http.delete<void>(url);
  }

  /**
   * Exibe uma mensagem rápida para o usuário usando snackbar do Angular Material.
   * @param msg Texto da mensagem a ser exibida
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center',  // Centraliza a mensagem horizontalmente
      verticalPosition: 'top',        // Exibe a mensagem no topo da tela
      duration: 6000,                 // Duração da mensagem em milissegundos
      panelClass: ['custom-snackbar'] // Classe CSS personalizada para o snackbar
    });
  }
}