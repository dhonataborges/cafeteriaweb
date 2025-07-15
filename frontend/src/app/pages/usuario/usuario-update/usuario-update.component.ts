import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioModel } from '../../../models/UsuarioModel';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-usuario-update',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, MatSnackBarModule],
  templateUrl: './usuario-update.component.html',
  styleUrls: ['./usuario-update.component.css'],
})
export class UsuarioUpdateComponent implements OnInit {
  usuario: UsuarioModel = {
    id: 0,
    nome: '',
    email: '',
    senha: null,
  };

  id_usuario: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.id_usuario = this.authService.getIdUsuario();

    if (this.id_usuario) {
      this.usuarioService.findById(parseInt(this.id_usuario)).subscribe({
        next: (res) => {
          this.usuario = res;
          this.usuario.senha = '';
        },
        error: () => {
          this.usuarioService.message('Erro ao carregar dados do usuário.');
          this.router.navigate(['/']);
        },
      });
    } else {
      this.usuarioService.message('ID do usuário não encontrado.');
      this.router.navigate(['/']);
    }
  }

  cancelarHover = false;
  mostrarSenha: boolean = false;
  update(formLogin: NgForm): void {
    if (formLogin.invalid) {
      this.usuarioService.message('Preencha todos os campos!');
      return;
    }

    if (!this.id_usuario) {
      this.usuarioService.message('ID do usuário não encontrado.');
      return;
    }

    // Se a senha estiver vazia ou só com espaços, define como null
    if (!this.usuario.senha || this.usuario.senha.trim() === '') {
      this.usuario.senha = null;
    }

    this.usuarioService.update(this.usuario).subscribe({
      next: () => {
        this.usuarioService.message('Usuário atualizado com sucesso!');
        this.authService.atualizarNomeUsuario(this.usuario.nome);
        this.router.navigate(['bebida-create']);
      },
      error: (err) => {
        const mensagem =
          err.error?.userMessage || 'Erro desconhecido ao atualizar o usuário.';
        this.usuarioService.message('Erro ao atualizar usuário: ' + mensagem);
      },
    });
  }

  cancelar(): void {
    this.router.navigate(['bebida-create']);
  }
}
