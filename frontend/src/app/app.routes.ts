import { Routes } from '@angular/router';
import { BebidaCreateComponent } from './pages/components/bebida/bebida-create/bebida-create.component';
import { HomeComponent } from './pages/components/home/home.component';

export const routes: Routes = [

 {path:'', component: HomeComponent},
 {path:'home', component: HomeComponent},
 {path:'bebida-create', component: BebidaCreateComponent}
];
