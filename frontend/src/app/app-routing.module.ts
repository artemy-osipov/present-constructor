import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AboutComponent } from 'app/about/about.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { PresentDetailComponent } from 'app/present-detail/present-detail.component';
import { PresentListComponent } from 'app/present-list/present-list.component';
import { PresentNewComponent } from 'app/present-new/present-new.component';

const routes: Routes = [
  { path: '', redirectTo: '/about', pathMatch: 'full' },
  { path: 'about', component: AboutComponent },
  { path: 'candies', component: CandyListComponent },
  { path: 'presents', component: PresentListComponent },
  { path: 'presents/new', component: PresentNewComponent },
  { path: 'presents/:id', component: PresentDetailComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})

export class AppRoutingModule {
}
