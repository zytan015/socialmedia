import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FileUploadService } from '../services/file-upload.service';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  selectImageFile!: File;
  getPid: any = [];
  selectedFiles?: FileList;
  currentFile?: File;
  dbFile: any[] = [];
  fileType!: string;
  fileByte!: Blob;
  message = '';
  format!: string;
  url!: any;
  link!: string;
  isActivate : boolean = false;

  constructor(private dialog: MatDialogRef<PostsComponent>, private uploadService: FileUploadService, private postService: PostService) { 
   }

  ngOnInit(): void {
  }

  
  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
    //this.dbFile =  event.target.files[0];
    console.log(this.selectedFiles?.item(0));
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.currentFile = file;
        this.fileType = this.currentFile.type;
        const reader = new FileReader();
        reader.readAsDataURL(file);
        if(file.type.indexOf('image')> -1){
          this.format = 'image';
        } else if(file.type.indexOf('video')> -1){
          this.format = 'video';
        }
        reader.onload = (event) => {
          this.url = (<FileReader>event.target).result;
        }
      }
    }

  }

  upload(commentInput: HTMLTextAreaElement): void {
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.currentFile = file;
        let comment = commentInput.value;
        if(comment.length<=0) {
          alert("Caption cannot be empty!");
          return;
        }
        else{
          console.log("show file", this.currentFile)
          this.uploadService.upload(this.currentFile).subscribe({
            next: (event: any) => {
                this.dbFile = event.body;
                console.log("dbFile ", event.body);
                if(this.dbFile.length != 0){
                  this.onPostClick(commentInput);
                }
            },
            error: (err: any) => {
              console.log(err);
              if (err.error && err.error.message) {
                this.message = err.error.message;
              } else {
                this.message = 'Could not upload the image!';
              }
              this.currentFile = undefined;
            },
          });
        }
       }
       this.selectedFiles = undefined; 
       return;
    }
    this.onPostLink(commentInput);
  }

  onPostClick(commentInput: HTMLTextAreaElement){
      let comment = commentInput.value;
      let data = {
        lastModifiedBy: localStorage.getItem("username"),
        createdBy: localStorage.getItem("username"),
        description: comment,
        dbFile: this.dbFile,
      }
      console.log("data: ", data)
      this.postService.createPost(data).subscribe(res=>{
        if(res.status == 201){
          window.alert('Post created successfully.');
          location.reload();
          this.dialog.close(PostsComponent);
        }
        else{
          window.alert(res.message);
        }
      })
  }

  activateInputLink(){
    this.isActivate =!this.isActivate;
  }

  onPostLink(commentInput: HTMLTextAreaElement){
    this.isActivate =!this.isActivate;
    let comment = commentInput.value;
    let data = {
      lastModifiedBy: localStorage.getItem("username"),
      createdBy: localStorage.getItem("username"),
      description: comment,
      link: this.link
    }
    console.log("data: ", data)
      this.postService.createPost(data).subscribe(res=>{
        if(res.status == 201){
          window.alert('Post created successfully.');
          location.reload();
          this.dialog.close(PostsComponent);
        }
        else{
          window.alert(res.message);
        }
      })
  }
}
