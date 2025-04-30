import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FlexLayoutModule } from 'ngx-flexible-layout';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './shared/material-module';
import { HttpClientModule } from '@angular/common/http';
import { NgxUiLoaderConfig, NgxUiLoaderModule, PB_DIRECTION, SPINNER } from 'ngx-ui-loader';
import { HomeComponent } from '../app/home/home.component';
import { LoginComponent } from '../app/login/login.component';
import { ConfirmationComponent } from './dialog/confirmation/confirmation.component';

const ngxUiLoaderConfig:NgxUiLoaderConfig={
  text:"Loading...",
  textColor:"white",
  textPosition:"center-center",
  pbColor:"white",
  bgsColor:"white",
  fgsColor:"white",
  fgsType:SPINNER.ballSpinClockwise,
  fgsSize:100,
  pbDirection:PB_DIRECTION.leftToRight,
  pbThickness: 5,

}


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    ConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule ,
    MaterialModule,
    HttpClientModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
