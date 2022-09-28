import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {ApiResponse} from "../model/api.response";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  authUrl: string = '/api';

  constructor(private http: HttpClient) { }

  login(data:any):Observable<ApiResponse>{
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<ApiResponse>(this.authUrl+ '/token/generate-token', data);
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    setTimeout(() => {
      location.reload();
    }, 800);
  }

  isUserLoggedIn() {
    let user = localStorage.getItem('currentUser');
    if (user === null) return false
    return true
  }

  getLoggedInemail() {
    let user = localStorage.getItem('currentUser')
    if (user === null) return ''
    return user
  }
}




