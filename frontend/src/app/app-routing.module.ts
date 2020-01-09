import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AboutComponent } from 'app/about/about.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { PresentDetailComponent } from 'app/present-detail/present-detail.component';
import { PresentListComponent } from 'app/present-list/present-list.component';
import { PresentNewComponent } from 'app/present-new/present-new.component';

import { AuthGuard, LoginComponent } from 'app/shared/security';

const routes: Routes = [
  { path: '', redirectTo: '/about', pathMatch: 'full' },
  { path: 'about', component: AboutComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'candies',
    component: CandyListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'candies/new',
    component: CandyEditComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'candies/:id',
    component: CandyEditComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'presents',
    component: PresentListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'presents/new',
    component: PresentNewComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'presents/show/:id',
    component: PresentDetailComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
