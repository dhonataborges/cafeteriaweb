import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

/**
 * Guard para proteger rotas que necessitam do usuário autenticado.
 * Retorna true se o usuário está logado, permitindo acesso à rota.
 * Caso contrário, bloqueia o acesso (retorna false).
 */
export const authGuard: CanActivateFn = (route, state) => {
  // Injeta o serviço de autenticação
  const authService = inject(AuthService);
  // Injeta o roteador para navegação (não utilizado aqui, mas útil para redirecionamentos)
  const router = inject(Router);

  // Verifica se o usuário está autenticado
  if (authService.estaLogado()) {
    return true;  // Permite ativar a rota
  }

  // Aqui poderia ser feito um redirecionamento para login, por exemplo:
  // router.navigate(['/login']);

  return false;  // Bloqueia a ativação da rota
};
