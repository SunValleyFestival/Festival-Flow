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
import {HttpAuthClient} from "./services/http/token/http-auth-client";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LocationDetailComponent,
    LoginComponent,
    HeaderComponent
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
