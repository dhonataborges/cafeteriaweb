// Importações de bibliotecas essenciais do Angular e serviços internos do projeto
import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioModel } from '../../../models/UsuarioModel';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-usuario-update', // Nome da tag do componente
  standalone: true, // Componente independente (sem necessidade de módulo)
  imports: [CommonModule, FormsModule, RouterModule, MatSnackBarModule], // Módulos importados
  templateUrl: './usuario-update.component.html', // Caminho para o HTML
  styleUrls: ['./usuario-update.component.css'], // Estilo do componente
})
export class UsuarioUpdateComponent implements OnInit {
  
  // Objeto que representa o usuário que será editado
  usuario: UsuarioModel = {
    id: 0,
    nome: '',
    email: '',
    senha: null, // A senha pode ser deixada vazia para não ser alterada
  };

  // Armazena o ID do usuário obtido do serviço de autenticação
  id_usuario: string | null = null;

  // Injeta dependências necessárias no construtor
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuarioService: UsuarioService,
    private authService: AuthService
  ) {}

  // Ciclo de vida do Angular – executado assim que o componente é inicializado
  ngOnInit(): void {
    // Obtém o ID do usuário logado a partir do AuthService
    this.id_usuario = this.authService.getIdUsuario();

    if (this.id_usuario) {
      // Busca o usuário pelo ID
      this.usuarioService.findById(parseInt(this.id_usuario)).subscribe({
        next: (res) => {
          this.usuario = res;
          this.usuario.senha = ''; // Limpa a senha para não exibir nem enviar senha antiga
        },
        error: () => {
          // Em caso de erro, exibe mensagem e redireciona para a página inicial
          this.usuarioService.message('Erro ao carregar dados do usuário.');
          this.router.navigate(['/']);
        },
      });
    } else {
      // Se o ID não for encontrado, redireciona para a página inicial
      this.usuarioService.message('ID do usuário não encontrado.');
      this.router.navigate(['/']);
    }
  }

  // Controla estilo de hover no botão cancelar (provavelmente usado no HTML)
  cancelarHover = false;

  // Define se a senha será exibida no campo de entrada (para mostrar/ocultar senha)
  mostrarSenha: boolean = false;

  // Método chamado ao enviar o formulário de atualização
  update(formLogin: NgForm): void {
    // Verifica se o formulário está inválido
    if (formLogin.invalid) {
      this.usuarioService.message('Preencha todos os campos!');
      return;
    }

    // Verifica se o ID do usuário está presente
    if (!this.id_usuario) {
      this.usuarioService.message('ID do usuário não encontrado.');
      return;
    }

    // Se a senha for vazia ou apenas espaços, define como null
    if (!this.usuario.senha || this.usuario.senha.trim() === '') {
      this.usuario.senha = null;
    }

    // Envia os dados atualizados para o backend
    this.usuarioService.update(this.usuario).subscribe({
      next: () => {
        // Exibe mensagem de sucesso
        this.usuarioService.message('Usuário atualizado com sucesso!');

        // Atualiza o nome do usuário na sessão atual
        this.authService.atualizarNomeUsuario(this.usuario.nome);

        // Redireciona para a tela de criação de bebida
        this.router.navigate(['bebida-create']);
      },
      error: (err) => {
        // Em caso de erro, exibe mensagem detalhada
        const mensagem =
          err.error?.userMessage || 'Erro desconhecido ao atualizar o usuário.';
        this.usuarioService.message('Erro ao atualizar usuário: ' + mensagem);
      },
    });
  }

  // Ação do botão de cancelar (volta para a tela de bebidas sem salvar alterações)
  cancelar(): void {
    this.router.navigate(['bebida-create']);
  }
}
