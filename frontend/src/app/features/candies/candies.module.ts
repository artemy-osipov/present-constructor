import { NgModule } from '@angular/core'

import { SharedModule } from 'app/shared/shared.module'

import { CandiesRoutingModule } from './candies-routing.module'
import { CandyEditComponent } from './candy-edit/candy-edit.component'
import { CandyListComponent } from './candy-list/candy-list.component'

@NgModule({
  declarations: [CandyEditComponent, CandyListComponent],
  imports: [SharedModule, CandiesRoutingModule],
})
export class CandiesModule {}
