import { Component, Injectable, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from '../../../../services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})

export class HeaderComponent implements OnInit {
  nomeUsuario: string | null = null;
  mostrarNome: boolean = false;
  private nomeSubscription?: Subscription;

  private rotasQueForcamLogout = ['/login', '/home', '/usuario-create'];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.nomeSubscription = this.authService.nomeUsuario$.subscribe((nome) => {
      this.nomeUsuario = nome;
      this.mostrarNome = !!nome;
    });

    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.verificarRotaEAtualizar();
      });

    this.verificarRotaEAtualizar();
  }

  ngOnDestroy(): void {
    this.nomeSubscription?.unsubscribe();
  }

  mostrarMinhaConta = false;

  toggleMinhaConta() {
    this.mostrarMinhaConta = !this.mostrarMinhaConta;
  }

  abrirAtualizarUsuario() {
    const id = this.authService.getIdUsuario();
    if (id) {
      this.router.navigate([`usuario-update/update/${id}`]);
      this.mostrarMinhaConta = false;
    } else {
      this.authService.message('Usuário não identificado');
    }
  }

  verificarRotaEAtualizar(): void {
    const rotaAtual = this.router.url;

    // Se a rota atual exige limpeza do login, faz logout automático
    if (this.rotasQueForcamLogout.includes(rotaAtual)) {
      this.authService.logout(false); // não redireciona
    }

    // Atualiza a exibição
    this.nomeUsuario = this.authService.getNomeUsuario();
    this.mostrarNome =
      !!this.nomeUsuario && !this.rotasQueForcamLogout.includes(rotaAtual);
  }

  sair(): void {
    this.authService.logout(); // logout com redirecionamento
    this.verificarRotaEAtualizar();
  }

  navegador(): void {
    this.router.navigate(['/login']);
  }
}
