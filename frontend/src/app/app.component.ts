import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './pages/components/template/header/header.component';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './pages/components/template/footer/footer.component';

@Component({  
  standalone: true,
  selector: 'app-root',
  imports: [
    RouterOutlet,
    CommonModule,
    HeaderComponent, 
    FooterComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'cafeteriaweb';
}
