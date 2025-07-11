import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usuario-create',
  imports: [],
  templateUrl: './usuario-create.component.html',
  styleUrl: './usuario-create.component.css'
})
export class UsuarioCreateComponent {
  constructor(
      private router: Router
    ) {}


    navegador():void {
      this.router.navigate(['login']);
    }
}
