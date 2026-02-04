import { Routes } from '@angular/router';
import { HomeComponent } from './shared/home/home.component';
import { authGuard } from './core/guards/auth.guards';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./shared/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./shared/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./shared/login/login.component').then(m => m.LoginComponent)
  }
];

