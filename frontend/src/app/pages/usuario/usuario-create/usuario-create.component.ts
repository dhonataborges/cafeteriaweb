import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UsuarioModel } from '../../../models/UsuarioModel';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-usuario-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, MatSnackBarModule],
  templateUrl: './usuario-create.component.html',
  styleUrls: ['./usuario-create.component.css'],
})
export class UsuarioCreateComponent {

  // Objeto que armazena os dados digitados pelo usuário no formulário
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

  /**
   * Cria um novo usuário e realiza login automático após o sucesso.
   * @param formLogin - Formulário de template-driven forms.
   */
  create(formLogin: NgForm) {
    // Verifica se o formulário está válido antes de prosseguir
    if (formLogin.invalid) {
      this.authService.message('Preencha todos os campos!');
      return;
    }

    // Cria o objeto de entrada para a API
    const usuarioInput = {
      nome: this.usuario.nome,
      email: this.usuario.email,
      senha: this.usuario.senha,
    };

    // Chama o serviço para criar o usuário
    this.usuarioService.create(usuarioInput).subscribe({
      next: (api) => {
        console.log('Usuário criado com sucesso!');

        // Monta as credenciais para o login automático
        const credenciais = {
          id: api.id,
          email: api.email,
          senha: this.usuario.senha || '', // Utiliza a senha digitada
        };

        // Faz login automaticamente após criar o usuário
        this.authService.fazerLogin(credenciais).subscribe({
          next: (res) => {
            console.log('Login automático realizado com sucesso!', res);
            this.router.navigate(['bebida-create']); // Redireciona para o sistema após login
          },
          error: (err) => {
            this.authService.message(
              'Erro ao realizar login automático: ' +
              (err.error?.message || '')
            );
          },
        });
      },

      // Trata erros ao criar usuário
      error: (err) => {
        if (err.status === 409) {
          // Erro 409 → e-mail já cadastrado
          this.authService.message('Erro: e-mail já cadastrado!');
        } else {
          // Outro erro inesperado
          this.authService.message(
            'Erro ao criar usuário: ' +
            (err.error?.message || 'Erro desconhecido')
          );
        }
      },
    });
  }

  /**
   * Navega manualmente para a tela de login (usado em botão "Já tenho conta").
   */
  navegador(): void {
    this.router.navigate(['login']);
  }
}
