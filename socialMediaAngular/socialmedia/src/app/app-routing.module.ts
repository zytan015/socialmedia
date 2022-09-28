import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ListUserComponent } from './list-user/list-user.component';
import { PostfeedComponent } from './postfeed/postfeed.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { AuthGuard } from './services/auth.guard';
import { ViewPostComponent } from './view-post/view-post.component';
import { AdminBoardComponent } from './admin-board/admin-board.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'post', component: PostfeedComponent },
  { path: 'admin', component: AdminBoardComponent, canActivate : [AuthGuard] },
  { path: 'list-users', component: ListUserComponent, canActivate : [AuthGuard]  },
  { path: 'list-posts', component: ViewPostComponent, canActivate : [AuthGuard]  },
  { path: 'edit', component: EditUserComponent },
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
