import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogBoxPostComponent } from '../dialog-box-post/dialog-box-post.component';
import { Post } from '../model/post.model';
import { PostData } from '../postfeed/postfeed.component';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  //listOfPost:any  = [];
  @Input() postData!: PostData;
  listOfPost:PostData[]  = [];
  selectedRow:any = [];
  post: Post[] =[];
  currentPage: string|number = 1;
  count: string|number = 10;
  
  constructor(private postService: PostService,  private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getPosts();
  }

  getPosts(){
    this.postService.getAllPosts().subscribe(data =>{  
      this.listOfPost = data.result ;
      this.listOfPost.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime());
      console.log("PostData: ", this.listOfPost);
    });
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
      else if(result.event == 'Delete'){
        this.deleteRowData(result.data);
      }
    });
  }

  deleteRowData(post: Post){
    this.postService.deletePost(post.pid)
      .subscribe( data => {
        this.post = this.post.filter(p => p !== post);
        window.alert("Post deleted");
        window.location.reload();
      })
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
}
