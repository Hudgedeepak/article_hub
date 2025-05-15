import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RouterGuardService } from './services/router-guard.service';
import { AdminModule } from './admin/admin.module';
import { UserRegisterComponent } from './user-register/user-register.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {path: 'register', component:UserRegisterComponent},
  {
    path: 'articleHub',
    loadChildren: () => import('../app/admin/admin.module').then(m => m.AdminModule),
    canActivate: [RouterGuardService]
  },
  { path: "**", component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
