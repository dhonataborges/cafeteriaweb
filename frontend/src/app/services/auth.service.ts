import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../environment.ts/environment';
import { UsuarioModel } from '../models/UsuarioModel';
import { decodeToken } from '../auth/jwt-utils';

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiUrl: string = environment.apiUrl;
  private readonly TOKEN_KEY = 'token';
  private readonly USER_KEY = 'user_info';

  private nomeUsuarioSubject: BehaviorSubject<string | null>;
  nomeUsuario$: Observable<string | null>;

  constructor(
    private http: HttpClient,
    private snack: MatSnackBar,
    private router: Router
  ) {
    const nome = typeof window !== 'undefined' ? this.getNomeUsuario() : null;
    this.nomeUsuarioSubject = new BehaviorSubject<string | null>(nome);
    this.nomeUsuario$ = this.nomeUsuarioSubject.asObservable();
  }

  fazerLogin(credentials: {
    email: string;
    senha: string;
  }): Observable<LoginResponse> {
    const url = `${this.apiUrl}/login`;
    return this.http
      .post<LoginResponse>(url, credentials)
      .pipe(tap((res) => this.login(res.token)));
  }

  private login(token: string): void {
    try {
      if (typeof window !== 'undefined') {
        localStorage.setItem(this.TOKEN_KEY, token);

        const decoded = decodeToken<UsuarioModel>(token);
        if (decoded?.id && decoded?.nome) {
          const userInfo = JSON.stringify({
            id: decoded.id,
            nome: decoded.nome,
          });
          localStorage.setItem(this.USER_KEY, userInfo);
          this.nomeUsuarioSubject.next(decoded.nome);
        }
      }
    } catch (error) {
      console.error('Erro ao processar o token JWT:', error);
      this.logout(false);
    }
  }

  logout(redirecionar: boolean = true): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
    }
    this.nomeUsuarioSubject.next(null);

    if (redirecionar) {
      this.router.navigate(['/login']);
    }
  }

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

  getToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem(this.TOKEN_KEY);
  }

  estaLogado(): boolean {
    return !!this.getToken();
  }

  getUsuarioLogado(): UsuarioModel | null {
    const token = this.getToken();
    if (!token) return null;
    return decodeToken<UsuarioModel>(token);
  }

  getNomeUsuario(): string | null {
    if (typeof window === 'undefined') return null;
    const dados = localStorage.getItem(this.USER_KEY);
    return dados ? JSON.parse(dados).nome : null;
  }

  getIdUsuario(): string | null {
    if (typeof window === 'undefined') return null;
    const dados = localStorage.getItem(this.USER_KEY);
    return dados ? JSON.parse(dados).id : null;
  }

  message(msg: String): void {
    this.snack.open(`${msg}`, 'OK', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 4000,
      panelClass: ['custom-snackbar'],
    });
  }
}
