// loading.interceptor.ts

import { HttpInterceptorFn, HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { inject } from '@angular/core';
import { LoadingService } from '../services/loading.service';

/**
 * Interceptor HTTP para controle do estado de carregamento global.
 * Mostra o indicador de loading quando uma requisição é iniciada
 * e oculta quando a requisição termina (seja sucesso ou erro).
 * 
 * @param req Requisição HTTP
 * @param next Próximo handler no pipeline
 * @returns Observable da requisição, com o controle do loading aplicado
 */
export const loadingInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<any> => {
  // Injeta o serviço de loading para controlar estado de carregamento
  const loadingService = inject(LoadingService);

  // Exibe o loading quando a requisição começa
  loadingService.show();

  // Processa a requisição e, ao final (success ou error), oculta o loading
  return next(req).pipe(
    finalize(() => loadingService.hide())
  );
};