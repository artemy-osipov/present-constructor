import { NgModule } from '@angular/core'

import { SharedModule } from 'app/shared/shared.module'

import { CandiesRoutingModule } from './candies-routing.module'
import { CandyEditComponent } from './candy-edit/candy-edit.component'
import { CandyListComponent } from './candy-list/candy-list.component'
import { CandyQuery, CandyService, CandyStore } from './state'

@NgModule({
  declarations: [CandyEditComponent, CandyListComponent],
  providers: [CandyQuery, CandyService, CandyStore],
  imports: [SharedModule, CandiesRoutingModule],
})
export class CandiesModule {}
