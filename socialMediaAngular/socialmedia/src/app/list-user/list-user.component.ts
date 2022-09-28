import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { ApiService } from '../services/api.service';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';
import { authUser } from '../model/auth-login.model';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  
 users: User[] =[];
 selectedRow:any = [];
 currentUser!: authUser;

  constructor(private router: Router, private apiService: ApiService, private dialog: MatDialog) {}

  ngOnInit(): void {
    if(!window.localStorage.getItem('currentUser')) {
      this.router.navigate(['login']);
      return;
    }
    this.apiService.getUsers()
      .subscribe( data => {
        this.users = data.result;
        console.log(this.users);
      });
  }

  deleteRowData(user: User){
    this.apiService.deleteUser(user.uid)
      .subscribe( data => {
        this.users = this.users.filter(u => u !== user);
        window.alert("User id "+user.uid + " deleted");
        window.location.reload();
      })
  }

  openDialog(action: String, obj: any) {
    obj.action = action;
    const dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '250px',
      data: obj
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.event == 'Update'){
        this.updateRowData(result.data);
      }
      else if(result.event == 'Delete'){
        this.deleteRowData(result.data);
      }
    });
  }
  
  updateRowData(item: any){
    this.selectedRow = item;
    console.log("selected row: ", this.selectedRow);
    this.apiService.updateUser(this.selectedRow).subscribe(data => {
      if(data.status === 200) {
        window.alert('User updated successfully.');
        window.location.reload();
      }else {
        window.alert(data.message);
      }
    },
    error => {
      window.alert("Please use a different email");
    });
}

}
