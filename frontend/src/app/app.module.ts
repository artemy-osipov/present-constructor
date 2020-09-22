import { NgModule } from '@angular/core'

import { CoreModule } from 'app/core/core.module'

import { AboutComponent } from 'app/about/about.component'
import { AppRoutingModule } from 'app/app-routing.module'
import { AppComponent } from 'app/app.component'
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component'
import { CandyListComponent } from 'app/candy-list/candy-list.component'
import { PresentDetailComponent } from 'app/present-detail/present-detail.component'
import { PresentListComponent } from 'app/present-list/present-list.component'
import { PresentNewComponent } from 'app/present-new/present-new.component'
import { PresentNewSelectCandyComponent } from 'app/present-new/select-candy/present-new-select-candy.component'
import { SharedModule } from 'app/shared/shared.module'

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    CandyListComponent,
    CandyEditComponent,
    PresentListComponent,
    PresentDetailComponent,
    PresentNewComponent,
    PresentNewSelectCandyComponent,
  ],
  imports: [
    CoreModule,
    AppRoutingModule,
    SharedModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
