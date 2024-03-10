import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './pages/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from "@angular/common/http";
import { HeaderComponent } from './header/header.component';
import { LocationDetailComponent } from './pages/location-detail/location-detail.component';
import { NotFoundComponent } from './pages/error/not-found/not-found.component';
import { AdminComponent } from './pages/admin-area/admin/admin.component';
import { ManageLocationComponent } from './pages/admin-area/manage-location/manage-location.component';
import { ManageUserComponent } from './pages/admin-area/manage-user/manage-user.component';
import {CreateLocationComponent} from "./pages/admin-area/create-location/create-location.component";
import {HttpAuthClient} from "./services/http/token/http-auth-client";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LocationDetailComponent,
    LoginComponent,
    HeaderComponent,
    NotFoundComponent,
    HeaderComponent,
    AdminComponent,
    ManageLocationComponent,
    ManageUserComponent,
    CreateLocationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [HttpAuthClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
