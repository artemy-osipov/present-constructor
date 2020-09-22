import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'

import { PresentDetailComponent } from './present-detail/present-detail.component'
import { PresentListComponent } from './present-list/present-list.component'
import { PresentNewComponent } from './present-new/present-new.component'

const routes: Routes = [
  { path: '', component: PresentListComponent },
  { path: 'new', component: PresentNewComponent },
  { path: ':id', component: PresentDetailComponent },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PresentsRoutingModule {}
