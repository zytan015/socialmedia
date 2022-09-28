import { HttpClient , HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../model/api.response';
import { Post } from '../model/post.model';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  
  baseUrl: string="/api";

  constructor(private http: HttpClient) { }  
  reqheaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ${currentUser}'
  })

  createPost(data: any): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + "/posts", data, {headers: this.reqheaders});
  }

  getAllPosts(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + "/posts/", {headers: this.reqheaders });
  }

  // updatePost(id: number, caption: string): Observable<ApiResponse> {
  //   return this.http.put<ApiResponse>(this.baseUrl + "/posts/"+ id, caption, {headers: this.reqheaders });
  // }

  deletePost(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + "/posts/" + id,  {headers: this.reqheaders });
  }

  updateView(id: number, count: number){
    return this.http.put<ApiResponse>(this.baseUrl + "/posts/views/" + id, count, {headers: this.reqheaders });
  }

  updatePost(id: number, caption: string, modifiedBy: string): Observable<any> {
      let params = new HttpParams()
      .set("caption", caption)
      .set("modifiedBy", modifiedBy)
    return this.http.put<any>(this.baseUrl + "/posts/" +id +"?" + params, {headers: this.reqheaders });
  }
}
