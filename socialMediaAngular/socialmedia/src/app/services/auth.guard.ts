import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor() { }
  public canActivate(route: ActivatedRouteSnapshot){
    let user = localStorage.getItem('role');
    if(user == 'ROLE_ADMIN'){
      return true;
    }
    return false;

  }


}
