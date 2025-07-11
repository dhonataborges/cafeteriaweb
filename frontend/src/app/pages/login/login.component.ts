import { Component } from '@angular/core';
import { Router } from '@angular/router';

// Importar FormsModule para usar ngModel
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginInput } from '../../models/input/LoginInput';
import { TokenModel } from '../../models/TokenModel';

@Component({
  selector: 'app-login',
  standalone: true,          
  imports: [FormsModule],   
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string = '';
  senha: string = '';
  erroLogin: string | null = null;

  redirectUrl: string | null = null; // Se quiser pegar query param, importe ActivatedRoute e trate depois

  constructor(private router: Router,
              private authService: AuthService
  ) {}

  entrar() {
    // Exemplo simples: validar campos básicos
   /*if (!this.email || !this.senha) {
      this.erroLogin = 'Preencha todos os campos!';
      return;
    }*/

    const credenciais: LoginInput = {
      email: this.email,
      senha: this.senha
    };


    // Aqui você chamaria seu serviço de login, exemplo:
    this.authService.fazerLogin(credenciais).subscribe({
      next: (api) => {
        console.log("Logado com sucesso!", api)
        this.router.navigate(['bebida-create']);
      },
       error: (err) => {
      this.authService.message('Usuário não cadastrado: ' + (err.error?.message || ''));
    }
    });

  
  }

  navegador(): void {
    this.router.navigate(['usuario-create']);
  }
}
