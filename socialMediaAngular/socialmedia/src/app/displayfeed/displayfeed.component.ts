import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogBoxPostComponent } from '../dialog-box-post/dialog-box-post.component';
import { Post } from '../model/post.model';
import { PostData } from '../postfeed/postfeed.component';
import { PostService } from '../services/post.service';


@Component({
  selector: 'app-displayfeed',
  templateUrl: './displayfeed.component.html',
  styleUrls: ['./displayfeed.component.css']
})
export class DisplayfeedComponent implements OnInit {
  @Input() postData!: PostData;
  
  getUser: string = "";
  caption: string = "";
  count: number = 0;  
  post: Post = new Post();

  constructor(private postService: PostService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getUser = JSON.parse(localStorage.getItem("username") || '{}');
   
  }

  openDialog(action: String, obj: any) {
    obj.action = action;
    const dialogRef = this.dialog.open(DialogBoxPostComponent, {
      width: '250px',
      data: obj
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result.event == 'Update'){
        this.updatePostDescription(result.data);
      }
    });
  }

  updatePostDescription(item: any){
    console.log("selected row: ", item.pid, item.description);
    let pid = item.pid;
    let caption = item.description;
    let modifiedBy = JSON.parse(localStorage.getItem('username') || '{}');
    this.postService.updatePost(pid, caption, modifiedBy).subscribe(data => {
      window.alert('Post updated successfully.');
      window.location.reload();
      if(data.status === 200) {
        window.alert('User updated successfully.');
        window.location.reload();
      }else {
        window.alert(data.message);
      }
    },
    error => {
      window.alert("An error occured!");
    });
  }

  onDeleteClick(pid: number) {
    console.log("Delete: " , pid);
    if(confirm("Are you sure to delete this post?")) {
      this.postService.deletePost(pid).subscribe(data=>{
        console.log(data);
        window.location.reload();
      })
    }
  }

  onViewClick(pid: number, count: number){
    count = (this.postData.views+= 1);
    this.postService.updateView(pid, count).subscribe(date=>{
      console.log("pid: " +pid+ ", views: "+count);
    })
  }



}
