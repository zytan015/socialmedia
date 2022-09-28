import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FirebaseTSApp } from 'firebasets/firebasetsApp/firebaseTSApp';
import { environment } from 'src/environments/environment';
import { HomeComponent } from './home/home.component';
import { AuthenticatorComponent } from './authenticator/authenticator.component';
import { ProfileComponent } from './profile/profile.component';
import { PostfeedComponent } from './postfeed/postfeed.component';
import { PostsComponent } from './posts/posts.component';
import { InterceptorService } from './services/interceptor.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { ListUserComponent } from './list-user/list-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { DisplayfeedComponent } from './displayfeed/displayfeed.component';
import { AddUserComponent } from './add-user/add-user.component';
import { ViewPostComponent } from './view-post/view-post.component';
import { DialogBoxPostComponent } from './dialog-box-post/dialog-box-post.component';
import { NgPipesModule } from 'ngx-pipes';
import { NgxPaginationModule } from 'ngx-pagination';
import { AdminBoardComponent } from './admin-board/admin-board.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthenticatorComponent,
    ProfileComponent,
    PostfeedComponent,
    PostsComponent,
    ListUserComponent,
    EditUserComponent,
    DialogBoxComponent,
    DisplayfeedComponent,
    AddUserComponent,
    ViewPostComponent,
    DialogBoxPostComponent,
    AdminBoardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatBottomSheetModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    NgPipesModule,
    NgxPaginationModule
  ],
  providers: [{
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(){
    FirebaseTSApp.init(environment.firebaseConfig)
  }
 }
