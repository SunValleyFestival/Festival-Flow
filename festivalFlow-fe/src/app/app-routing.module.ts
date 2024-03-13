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

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'location/:location', component: LocationDetailComponent, canActivate: [authGuard]},
  {path: 'admin/location/:name/:location', component: ManageLocationComponent, canActivate: [authGuard]},
  {path: 'user', component: ManageUserComponent, canActivate: [authGuard]},
  {path: 'create', component: CreateLocationComponent, canActivate: [authGuard]},
  {path: 'admin', component: AdminComponent, canActivate: [authGuard]},
  {path: 'admin/:day', component: AdminComponent, canActivate: [authGuard]},
  {path: 'location/:location/:day', component: LocationDetailComponent, canActivate: [authGuard]},
  {path: 'not-found', component: NotFoundComponent},
  {path: '', component: HomeComponent, canActivate: [authGuard]},
  {path: ':day', component: HomeComponent, canActivate: [authGuard]},
  {path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
