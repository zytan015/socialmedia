import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../model/api.response';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  baseUrl: string="/api";

  reqheaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ${currentUser}'
  })

  constructor(private http: HttpClient) { }

  createUser(data:any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl+ '/user/registration', data );
  }

  getUsers() : Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl+ '/admin/all', {headers: this.reqheaders});
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + "/admin/" + user.uid, user, {headers: this.reqheaders});
  }

  changePassword(user: User): Observable<ApiResponse> {
    const headers = { 'Authorization': 'currentUser' };
    return this.http.put<ApiResponse>(this.baseUrl + "/user/changePassword" , {headers: this.reqheaders});
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl +"/admin/" + id, {headers: this.reqheaders});
  }

  forgotPassword(email: string): Observable<ApiResponse> {
    let params = new HttpParams().set("email", email)
    return this.http.post<ApiResponse>(this.baseUrl +"/forgotpassword/?" + params , {headers: this.reqheaders});
  }

  resetPassword(token: string, password: string): Observable<ApiResponse> {
    let params = new HttpParams()
      .set("token", token)
      .set("password", password)
    return this.http.post<ApiResponse>(this.baseUrl +"/resetpassword/?" + params , {headers: this.reqheaders});
  }
}
