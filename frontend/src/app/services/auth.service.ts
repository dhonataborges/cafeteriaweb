import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../environment.ts/environment.prod';
import { UsuarioModel } from '../models/UsuarioModel';
import { decodeToken } from '../auth/jwt-utils';

// Interface para definir o formato da resposta do login (contendo token JWT)
interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root', // Serviço disponível em toda a aplicação
})
export class AuthService {
  // URL base da API, definida no arquivo environment.prod.ts
  apiUrl: String = environment.apiBaseUrl;

  // Chaves usadas para armazenar dados no localStorage
  private readonly TOKEN_KEY = 'token';
  private readonly USER_KEY = 'user_info';

  // BehaviorSubject para emitir o nome do usuário autenticado
  private nomeUsuarioSubject: BehaviorSubject<string | null>;
  // Observable que componentes podem assinar para receber atualizações do nome
  nomeUsuario$: Observable<string | null>;

  constructor(
    private http: HttpClient,
    private snack: MatSnackBar,
    private router: Router
  ) {
    // Inicializa o BehaviorSubject com o nome salvo no localStorage, se existir
    const nome = typeof window !== 'undefined' ? this.getNomeUsuario() : null;
    this.nomeUsuarioSubject = new BehaviorSubject<string | null>(nome);
    this.nomeUsuario$ = this.nomeUsuarioSubject.asObservable();
  }

  /**
   * Faz login na API enviando email e senha.
   * Ao receber o token JWT, chama o método privado 'login' para salvar dados.
   */
  fazerLogin(credentials: { email: string; senha: string }): Observable<LoginResponse> {
    const url = `${this.apiUrl}/login`;
    return this.http
      .post<LoginResponse>(url, credentials)
      .pipe(tap((res) => this.login(res.token)));
  }

  /**
   * Salva o token JWT e informações do usuário no localStorage.
   * Atualiza o BehaviorSubject para refletir o nome do usuário atual.
   */
  private login(token: string): void {
    try {
      if (typeof window !== 'undefined') {
        // Salva token JWT no localStorage
        localStorage.setItem(this.TOKEN_KEY, token);

        // Decodifica token para extrair informações do usuário
        const decoded = decodeToken<UsuarioModel>(token);
        if (decoded?.id && decoded?.nome) {
          const userInfo = JSON.stringify({
            id: decoded.id,
            nome: decoded.nome,
          });
          // Salva informações do usuário no localStorage
          localStorage.setItem(this.USER_KEY, userInfo);

          // Atualiza BehaviorSubject para notificar componentes inscritos
          this.nomeUsuarioSubject.next(decoded.nome);
        }
      }
    } catch (error) {
      console.error('Erro ao processar o token JWT:', error);
      this.logout(false); // Limpa sessão se token inválido
    }
  }

  /**
   * Realiza logout limpando dados do localStorage e opcionalmente redireciona para a tela de login.
   * @param redirecionar Define se deve redirecionar após logout (padrão: true)
   */
  logout(redirecionar: boolean = true): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
    }
    this.nomeUsuarioSubject.next(null); // Limpa o nome do usuário

    if (redirecionar) {
      this.router.navigate(['/login']);
    }
  }

  /**
   * Atualiza o nome do usuário armazenado no localStorage e notifica componentes assinantes.
   */
  atualizarNomeUsuario(nome: string): void {
    if (typeof window === 'undefined') return;

    const dados = localStorage.getItem(this.USER_KEY);
    if (dados) {
      const userInfo = JSON.parse(dados);
      userInfo.nome = nome;
      localStorage.setItem(this.USER_KEY, JSON.stringify(userInfo));
      this.nomeUsuarioSubject.next(nome);
    }
  }

  /**
   * Retorna o token JWT armazenado, ou null caso não exista.
   */
  getToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem(this.TOKEN_KEY);
  }

  /**
   * Indica se o usuário está logado verificando existência do token.
   */
  estaLogado(): boolean {
    return !!this.getToken();
  }

  /**
   * Retorna os dados do usuário decodificados do token JWT ou null se não houver token.
   */
  getUsuarioLogado(): UsuarioModel | null {
    const token = this.getToken();
    if (!token) return null;
    return decodeToken<UsuarioModel>(token);
  }

  /**
   * Retorna o nome do usuário armazenado no localStorage ou null.
   */
  getNomeUsuario(): string | null {
    if (typeof window === 'undefined') return null;
    const dados = localStorage.getItem(this.USER_KEY);
    return dados ? JSON.parse(dados).nome : null;
  }

  /**
   * Retorna o ID do usuário armazenado no localStorage ou null.
   */
  getIdUsuario(): string | null {
    if (typeof window === 'undefined') return null;
    const dados = localStorage.getItem(this.USER_KEY);
    return dados ? JSON.parse(dados).id : null;
  }

  /**
   * Exibe mensagem ao usuário usando o snackbar do Angular Material.
   * @param msg Texto da mensagem
   */
  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 4000, // Duração em milissegundos
      panelClass: ['custom-snackbar'], // Classe CSS customizada para o estilo do snackbar
    });
  }
}