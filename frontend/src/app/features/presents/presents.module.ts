import { NgModule } from '@angular/core'

import { SharedModule } from 'app/shared/shared.module'

import { PresentsRoutingModule } from './presents-routing.module'
import { PresentDetailComponent } from './present-detail/present-detail.component'
import { PresentListComponent } from './present-list/present-list.component'
import { PresentNewComponent } from './present-new/present-new.component'
import { PresentNewSelectCandyComponent } from './present-new/present-new-select-candy.component'
import { PresentItemControlComponent } from './present-new/present-item-control.component'
import { PresentService } from './service/present.service'

@NgModule({
  declarations: [
    PresentDetailComponent,
    PresentListComponent,
    PresentNewComponent,
    PresentNewSelectCandyComponent,
    PresentItemControlComponent,
  ],
  providers: [PresentService],
  imports: [SharedModule, PresentsRoutingModule],
})
export class PresentsModule {}
