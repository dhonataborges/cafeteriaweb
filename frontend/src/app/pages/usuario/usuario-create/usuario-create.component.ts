import { Component, Injectable } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UsuarioModel } from '../../../models/UsuarioModel';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-usuario-create',
  imports: [CommonModule, FormsModule, RouterModule, MatSnackBarModule],
  standalone: true,
  templateUrl: './usuario-create.component.html',
  styleUrls: ['./usuario-create.component.css'],
})

export class UsuarioCreateComponent {
  usuario: UsuarioModel = {
    nome: '',
    email: '',
    senha: '',
  };

  constructor(
    private router: Router,
    private usuarioService: UsuarioService,
    private authService: AuthService
  ) {}

  create(formLogin: NgForm) {
    // Exemplo simples: validar campos básicos
    if (formLogin.invalid) {
      this.authService.message('Preecha todos os campos!');
      return;
    }

    const usuarioInput = {
      nome: this.usuario.nome,
      email: this.usuario.email,
      senha: this.usuario.senha,
    };

    this.usuarioService.create(usuarioInput).subscribe({
      next: (api) => {
        console.log('Usuário criado com sucesso!');

        // Monta as credenciais para login automático
        const credenciais = {
          id: api.id,
          email: api.email,
          senha: this.usuario.senha || '', // usa a senha que o usuário digitou no cadastro
        };

        // Faz o login automático
        this.authService.fazerLogin(credenciais).subscribe({
          next: (res) => {
            console.log('Login automático realizado com sucesso!', res);
            this.router.navigate(['bebida-create']); // redireciona para sistema
          },
          error: (err) => {
            this.authService.message(
              'Erro ao realizar login automático: ' + (err.error?.message || '')
            );
          },
        });
      },
      error: (err) => {
        if (err.status === 409) {
          this.authService.message('Erro: e-mail já cadastrado!');
        } else {
          this.authService.message(
            'Erro ao criar usuário: ' +
              (err.error?.message || 'Erro desconhecido')
          );
        }
      },
    });
  }

  navegador(): void {
    this.router.navigate(['login']);
  }
}
