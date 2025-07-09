import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({  
  standalone: true,
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

constructor(
    private router: Router
  ) {}


  navegador():void {
    this.router.navigate(['login']);
  }

}
