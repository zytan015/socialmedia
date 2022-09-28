import { Component, Inject, OnInit, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Post } from '../model/post.model';

@Component({
  selector: 'app-dialog-box-post',
  templateUrl: './dialog-box-post.component.html',
  styleUrls: ['./dialog-box-post.component.css']
})
export class DialogBoxPostComponent implements OnInit {

  action!: string;
  local_data:any;
  
  constructor(public dialogRef: MatDialogRef<DialogBoxPostComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Post) {
      console.log("data: ", data);
      this.local_data = {...data};
      this.action = this.local_data.action;
   }

  ngOnInit(): void {
  }

  doAction(){
    this.dialogRef.close({event:this.action, data:this.local_data});
  }

  closeDialog(){
    this.dialogRef.close({event:'Cancel'});
  }

}
