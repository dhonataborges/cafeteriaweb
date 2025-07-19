import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * Interceptor HTTP que adiciona o token JWT no cabeçalho Authorization das requisições,
 * caso o token esteja presente no serviço de autenticação.
 * 
 * @param req Requisição HTTP original
 * @param next Próximo handler no pipeline de interceptação
 * @returns Observable da requisição interceptada ou original
 */
export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<any> => {
  // Injeta o serviço de autenticação para obter o token
  const authService = inject(AuthService);
  const token = authService.getToken();

  // Se existir token, clona a requisição adicionando o cabeçalho Authorization
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}` // Formato padrão do token JWT
      }
    });
    return next(authReq); // Passa a requisição modificada para o próximo handler
  }

  // Se não tiver token, segue com a requisição original
  return next(req);
};