// loading.service.ts
import { Injectable, Signal, signal } from '@angular/core';

@Injectable({
  providedIn: 'root' // Serviço disponível globalmente na aplicação
})
export class LoadingService {
  // Sinal privado que representa o estado atual do loading (true = exibindo, false = oculto)
  private _loading = signal(false);

  // Sinal público somente leitura para componentes que desejam escutar o estado do loading
  readonly loading: Signal<boolean> = this._loading.asReadonly();

  /**
   * Ativa o loading, configurando o sinal como true.
   * Normalmente usado para indicar que uma operação está em andamento.
   */
  show() {
    this._loading.set(true);
  }

  /**
   * Desativa o loading, configurando o sinal como false.
   * Normalmente usado para indicar que uma operação foi concluída.
   */
  hide() {
    this._loading.set(false);
  }
}