import { Routes } from '@angular/router';
import { BebidaCreateComponent } from './pages/components/bebida/bebida-create/bebida-create.component';
import { HomeComponent } from './pages/components/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { UsuarioCreateComponent } from './pages/usuario/usuario-create/usuario-create.component';

export const routes: Routes = [

 {path:'', component: HomeComponent},
 {path:'home', component: HomeComponent},
 {path:'login', component: LoginComponent},
 {path:'usuario-create', component: UsuarioCreateComponent},
 {path:'bebida-create', component: BebidaCreateComponent}
];
