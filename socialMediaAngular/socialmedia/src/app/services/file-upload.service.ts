import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  baseUrl: string="/api";
  //uploadUrl: string = "/uploadFile";

  constructor(private http: HttpClient) { }

  // upload(file: File, pid: any): Observable<HttpEvent<any>> {
  //   const formData: FormData = new FormData();
  //   formData.append('file', file);
  //   const req = new HttpRequest('POST', this.baseUrl + "/uploadFile/" + pid, formData, {
  //     reportProgress: true,
  //     responseType: 'json',
  //   });
  //   return this.http.request(req);
  // }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    const req = new HttpRequest('POST', this.baseUrl + "/uploadFile", formData, {
      reportProgress: true,
      responseType: 'json',
    });
    return this.http.request(req);
  }

  // getFiles(): Observable<any> {
  //   return this.http.get(`${this.baseUrl}/files`);
  // }
}
