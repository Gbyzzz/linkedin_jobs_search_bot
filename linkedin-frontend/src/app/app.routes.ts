import {Routes} from '@angular/router';
import {LoginComponent} from './view/login/login.component';
import {MainComponent} from './view/main/main.component';
import {AuthGuard} from './security/auth.guard';
import {UsersComponent} from './view/main/users/users.component';
import {SearchParamsComponent} from './view/main/search-params/search-params.component';
import {JobsComponent} from './view/main/jobs/jobs.component';
import {UserPageComponent} from './view/main/user-page/user-page.component';

export const routes: Routes = [
  {path: 'login', component: LoginComponent},

  {path: '', component: MainComponent, canActivate: [AuthGuard],  data: {roles: ['ADMIN', 'USER']} },
  {path: 'user-page', component: UserPageComponent, canActivate: [AuthGuard],  data: {roles: ['ADMIN', 'USER']} },
  {path: '', component: UsersComponent, canActivate: [AuthGuard],  data: {roles: ['ADMIN', 'USER']} },
  {path: '', component: SearchParamsComponent, canActivate: [AuthGuard],  data: {roles: ['ADMIN', 'USER']} },
  {path: '', component: JobsComponent, canActivate: [AuthGuard],  data: {roles: ['ADMIN', 'USER']} },

  {path: '**',redirectTo: '', component: MainComponent}
];
