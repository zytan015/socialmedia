import { Component, OnInit } from '@angular/core';
import { MatBottomSheet} from '@angular/material/bottom-sheet';
import { AuthenticatorComponent } from '../authenticator/authenticator.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private logInSheet: MatBottomSheet) { }

  ngOnInit(): void {
  }

  getStarted(){
    this.logInSheet.open(AuthenticatorComponent);
  }

}
