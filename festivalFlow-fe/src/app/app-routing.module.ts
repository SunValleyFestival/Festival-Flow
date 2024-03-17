import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {authGuard} from './auth/auth.guard';
import {LocationDetailComponent} from "./pages/location-detail/location-detail.component";
import {NotFoundComponent} from "./pages/error/not-found/not-found.component";
import {AdminComponent} from "./pages/admin-area/admin/admin.component";
import {ManageLocationComponent} from "./pages/admin-area/manage-location/manage-location.component";
import {ManageUserComponent} from "./pages/admin-area/manage-user/manage-user.component";
import {CreateLocationComponent} from "./pages/admin-area/create-location/create-location.component";
import {UserDetailComponent} from "./pages/admin-area/user-detail/user-detail.component";
import {UserComponent} from "./pages/user/user.component";

const route: Routes = [
  {path: 'user/login', component: LoginComponent},
  {path: 'admin/location/:name/:location', component: ManageLocationComponent, canActivate: [authGuard]},
  {path: 'admin/user', component: ManageUserComponent, canActivate: [authGuard]},
  {path: 'admin/user/:id', component: UserDetailComponent, canActivate: [authGuard]},
  {path: 'admin/create', component: CreateLocationComponent, canActivate: [authGuard]},
  {path: 'admin', component: AdminComponent, canActivate: [authGuard]},
  {path: 'admin/:day', component: AdminComponent, canActivate: [authGuard]},
  {path: 'user/location/:location', component: LocationDetailComponent, canActivate: [authGuard]},
  {path: 'user/user', component: UserComponent, canActivate: [authGuard]},
  {path: 'user', component: HomeComponent, canActivate: [authGuard]},
  {path: 'user/:day', component: HomeComponent, canActivate: [authGuard]},
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: 'user'}
];

const routes: Routes = [
  {
    path: 'user',
    component: HomeComponent,
    canActivate: [authGuard],
    children: [
      {path: '', component: HomeComponent}, // Default path under 'user'
      {path: ':day', component: HomeComponent}, // Dynamic path for 'user/:day'
      {path: 'location/:location', component: LocationDetailComponent},
      {path: 'user', component: UserComponent}, // 'user/user' for clarity
    ]
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [authGuard],
    children: [
      {path: '', component: AdminComponent}, // Default path under 'admin'
      {path: ':day', component: AdminComponent}, // Dynamic path for 'admin/:day'
      {path: 'create', component: CreateLocationComponent},
      {path: 'location/:name/:location', component: ManageLocationComponent},
      {path: 'user', component: ManageUserComponent},
      {path: 'user/:id', component: UserDetailComponent},
    ]
  },
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: 'user'},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
