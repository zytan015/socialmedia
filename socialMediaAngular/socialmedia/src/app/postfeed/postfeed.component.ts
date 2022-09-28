import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PostsComponent } from '../posts/posts.component';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-postfeed',
  templateUrl: './postfeed.component.html',
  styleUrls: ['./postfeed.component.css']
})
export class PostfeedComponent implements OnInit {

  listOfPost:PostData[]  = [];
  isUser: boolean = false;
  currentPage: string|number = 1;
  count: string|number = 10;
  constructor(private dialog: MatDialog, private postService: PostService) { }

  ngOnInit(): void {
    this.getPosts();
  }
  
  onCreatePostClick(){
    this.dialog.open(PostsComponent)
  }

  checkPostBelongsToUser(){
    let getUserEmail = localStorage.getItem("username");
    for(let i=0; i<this.listOfPost.length ; i++){
        if(this.listOfPost[i].createdBy == getUserEmail){
          this.isUser = true;
        }
    }
  }

  getPosts(){
    this.postService.getAllPosts().subscribe(data =>{  
      let link =  data.result.pid;
      this.listOfPost = data.result;
      this.listOfPost.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime()); //sort the post to display the latest post at the top
      console.log("PostData: ", this.listOfPost);
    });
    
  }
}

export interface PostData{
  pid: number;
  views: number;
  description: string;
  createdBy: string;
  creationDate: string;
  lastModifiedDate: string;
  lastModifiedBy: string;
  dbFile: any;
  link: string;
  fileType: string;
  dataType: string;
}