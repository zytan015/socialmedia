import { Component, OnInit } from '@angular/core';
import { MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ApiService } from '../services/api.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-authenticator',
  templateUrl: './authenticator.component.html',
  styleUrls: ['./authenticator.component.css']
})
export class AuthenticatorComponent implements OnInit {
  
  state=AuthenticatorComponentState.LOGIN;

  username!: string;
  password!: string;
  errorMessage = 'Invalid Credentials';
  successMessage!: string;
  invalidLogin = false;
  invalidResgitration = false;
  pwdMismatch = false;
  form!: FormGroup;

  email = new FormControl('', [Validators.required, Validators.email])
  uname = new FormControl('',[Validators.required] )
  pwd = new FormControl('',[Validators.required] )
  cfmPwd = new FormControl('',[Validators.required] )
  token = new FormControl('',[Validators.required] )

  constructor( private bottomSheetRef: MatBottomSheetRef,
    private router: Router,
    private authService: AuthService,
    private apiService: ApiService) { }

  ngOnInit(): void {
  }

  forgotPwdBtn(){
    this.state=AuthenticatorComponentState.FORGOT_PASSWORD;
  }

  resetPwdBtn(){
    this.state=AuthenticatorComponentState.RESET_PASSWORD;
  }

  createAccBtn(){
    this.state=AuthenticatorComponentState.REGISTER;
  }

  loginBtn(){
    this.state=AuthenticatorComponentState.LOGIN;
  }

  isLogin(){
    return this.state==AuthenticatorComponentState.LOGIN;
  }

  isRegister(){
    return this.state==AuthenticatorComponentState.REGISTER;
  }

  isForgotPwd(){
    return this.state==AuthenticatorComponentState.FORGOT_PASSWORD;
  }

  isRestPassword(){
    return this.state==AuthenticatorComponentState.RESET_PASSWORD;
  }

  getErrorMessage() {
    if (this.email.hasError('required') ||  this.pwd.hasError('required') || this.uname.hasError('required') || this.cfmPwd.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  getStateText(){
    switch(this.state){
      case AuthenticatorComponentState.LOGIN: return "Login"
      case AuthenticatorComponentState.REGISTER: return "Register"
      case AuthenticatorComponentState.FORGOT_PASSWORD: return "Forgot Password"
      case AuthenticatorComponentState.RESET_PASSWORD: return "Reset Password"
    }
  }

  onResetPassword(resetPwdToken: HTMLInputElement, resetPwd: HTMLInputElement){
    let token = resetPwdToken.value;
    let password = resetPwd.value;
    if(this.isNotEmpty(token) && this.isNotEmpty(password)){
      this.apiService.resetPassword(token, password).subscribe(data => {
        if(data.status == 200){
          window.alert(data.message);
          this.bottomSheetRef.dismiss(true);
        }
        else{
          window.alert(data.message);
          this.bottomSheetRef.dismiss(true);
        }
      });
    }
  }

  onReset(resetEmail: HTMLInputElement){
    let email=resetEmail.value;
    if(this.isNotEmpty(email)){
      this.apiService.forgotPassword(email).subscribe(data => {
        if(data.status == 200){
          window.alert(data.message);
          this.bottomSheetRef.dismiss(true);
        }
        else{
          window.alert(data.message);
          this.bottomSheetRef.dismiss(true);
        }
      });
    }
  }

  onLogin(loginEmail: HTMLInputElement,
    loginPwd: HTMLInputElement){
      const data={
        email:loginEmail.value,
        password:loginPwd.value
      }
      console.log(data);
      if(this.isNotEmpty(data.email) && this.isNotEmpty(data.password)){
        this.authService.login(data).subscribe(data => {
          if(data.status === 200) {
            localStorage.setItem('currentUser', data.result.token);
            localStorage.setItem('username', JSON.stringify(data.result.username));
            localStorage.setItem('role', data.result.role);
            this.invalidLogin = false;
            console.log(this.invalidLogin);
            if(data.result.role == "ROLE_USER"){
              this.router.navigate(['post']);
              setTimeout(() => {
                location.reload();
              }, 800);
            }
            else{
              this.router.navigate(['admin']);
            }
            this.bottomSheetRef.dismiss(true);
          } else {
            this.invalidLogin = false;
            console.log(this.invalidLogin);
          }
        });
        error:(err: any) => {
          this.invalidLogin = true;
          console.log(this.invalidLogin);
        }
    }
  }

  register(registerUsername: HTMLInputElement,
    registerEmail: HTMLInputElement, 
    registerPwd: HTMLInputElement,
    registerCfmPwd: HTMLInputElement){
      const data = {
        username:registerUsername.value,
        email:registerEmail.value,
        password:registerPwd.value,
        cfmPassword:registerCfmPwd.value
      }
      if(this.isNotEmpty(data.username) && this.isNotEmpty(data.email) && this.isNotEmpty(data.password) &&  
        this.isNotEmpty(data.cfmPassword) && this.isMatch(data.password, data.cfmPassword)){
        this.pwdMismatch = false;
        this.apiService.createUser(data).subscribe(data => {
          if(data.status == 200){
            window.alert('Account creation successful.');
            this.bottomSheetRef.dismiss(true);
            this.invalidResgitration = false;
          }
          else{
            window.alert(data.message);
            this.invalidResgitration = true;
            this.bottomSheetRef.dismiss(true);
          }
        }) 
    }else if (!this.isMatch(data.password, data.cfmPassword)){
        this.pwdMismatch = true;
    }
  }

  isNotEmpty(text: string){
    return text!=null && text.length>0
  }

  isMatch(text: string, compareWith: string){
    return text==compareWith
  }
}

export enum AuthenticatorComponentState{
  LOGIN, 
  REGISTER,
  FORGOT_PASSWORD,
  RESET_PASSWORD
}
