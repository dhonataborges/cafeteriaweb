import { Component, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './pages/components/template/header/header.component';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './pages/components/template/footer/footer.component';
import { LoadingComponent } from "./pages/loading/loading.component";
import { LoadingService } from './services/loading.service';

@Component({
  standalone: true,
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, HeaderComponent, FooterComponent, LoadingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {

  title = 'cafeteriaweb';

  private loadingService = inject(LoadingService);
  isLoading = computed(() => this.loadingService.loading());
  
}
