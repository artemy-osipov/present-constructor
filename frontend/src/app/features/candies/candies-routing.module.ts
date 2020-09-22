import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'

import { CandyEditComponent } from './candy-edit/candy-edit.component'
import { CandyListComponent } from './candy-list/candy-list.component'

const routes: Routes = [
  { path: '', component: CandyListComponent },
  { path: 'new', component: CandyEditComponent },
  { path: ':id', component: CandyEditComponent },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CandiesRoutingModule {}
