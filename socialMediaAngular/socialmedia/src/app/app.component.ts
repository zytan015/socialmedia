import { Component } from '@angular/core';
import { MatBottomSheet} from '@angular/material/bottom-sheet';
import { AuthenticatorComponent } from './authenticator/authenticator.component';
import { FirebaseTSAuth } from 'firebasets/firebasetsAuth/firebaseTSAuth';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'socialmedia';
  auth = new FirebaseTSAuth();
  authenticated = false;
  isLoggedIn = false;
  isAdmin= false;
  email: string = "";
  constructor(private logInSheet: MatBottomSheet,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthService) {
  }

  ngOnInit() {
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    this.email = JSON.parse(localStorage.getItem('username') || '{}');
    if(localStorage.getItem('role')=="ROLE_ADMIN"){
      this.isAdmin=true;
    }
  }

  loggedIn(){
    return this.authenticationService.isUserLoggedIn();
  }

  login(){
    this.logInSheet.open(AuthenticatorComponent);
  }

  logout(){
    this.authenticationService.logout();
    this.router.navigate(['']);
  }
  
}
