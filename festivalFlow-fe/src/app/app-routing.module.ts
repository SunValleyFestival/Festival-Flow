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
import {AboutComponent} from "./pages/about/about.component";
import {UserTurnsComponent} from "./pages/user-turns/user-turns.component";
import {AddUserComponent} from "./pages/admin-area/add-user/add-user.component";

const routes: Routes = [
  {path: 'user/login', component: LoginComponent},
  {path: 'admin/location/:location', component: ManageLocationComponent},
  {path: 'admin/user', component: ManageUserComponent},
  {path: 'admin/user/:id', component: UserDetailComponent},
  {path: 'admin/create', component: CreateLocationComponent},
  {path: 'admin/add-user', component: AddUserComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'admin/:day', component: AdminComponent},
  {path: 'user/turns', component: UserTurnsComponent, canActivate: [authGuard]},
  {path: 'user/about', component: AboutComponent},
  {path: 'user/location/:location', component: LocationDetailComponent, canActivate: [authGuard]},
  {path: 'user/user', component: UserComponent, canActivate: [authGuard]},
  {path: 'user', component: HomeComponent, canActivate: [authGuard]},
  {path: 'user/:day', component: HomeComponent, canActivate: [authGuard]},
  {path: 'user', redirectTo: 'user', pathMatch: 'full'},
  {path: 'not-found', component: NotFoundComponent},
  {path: '', redirectTo: 'user', pathMatch: 'full'},
  {path: '**', redirectTo: 'user'}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
