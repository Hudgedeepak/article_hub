import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AppUserService {

  url=environment.apiUrl;
  constructor(private httpClient:HttpClient) { }

  login(data:any){
    return this.httpClient.post(this.url+"/appUser/login",data,{
      headers: new HttpHeaders().set('content-Type', "application/json") })
  }
}
