import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../environment.ts/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  apiUrl: string = environment.apiUrl;
  private readonly TOKEN_KEY = 'token';

  constructor(
    private http: HttpClient,
    private snack: MatSnackBar
  ) { }

  fazerLogin(credentials: { email: string; senha: string }): Observable<LoginResponse> {
  const url = `${this.apiUrl}/login`;
  return this.http.post<LoginResponse>(url, credentials).pipe(
    tap(res => this.login(res.token)) // salva token automaticamente após login
  );
  }

  login(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  estaLogado(): boolean {
    return !!this.getToken();
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
