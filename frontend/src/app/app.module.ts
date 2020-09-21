import { HttpClientModule } from '@angular/common/http'
import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatDialogModule } from '@angular/material/dialog'
import {
  FaIconLibrary,
  FontAwesomeModule,
} from '@fortawesome/angular-fontawesome'

import { CoreModule } from 'app/core/core.module'

import { AboutComponent } from 'app/about/about.component'
import { AppRoutingModule } from 'app/app-routing.module'
import { AppComponent } from 'app/app.component'
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component'
import { CandyListComponent } from 'app/candy-list/candy-list.component'
import { ConfirmationDeleteComponent } from 'app/confirmation-delete/confirmation-delete.component'
import { LinkBorderComponent } from 'app/link-border/link-border.component'
import { PresentDetailComponent } from 'app/present-detail/present-detail.component'
import { PresentListComponent } from 'app/present-list/present-list.component'
import { PresentNewComponent } from 'app/present-new/present-new.component'
import { PresentNewSelectCandyComponent } from 'app/present-new/select-candy/present-new-select-candy.component'
import { ValidationModule } from 'app/shared/validation'

import {
  faAngleDown,
  faAngleUp,
  faCopy,
  faEye,
  faFile,
  faPencilAlt,
  faPlus,
  faRubleSign,
  faTimes,
  faTrash,
} from '@fortawesome/free-solid-svg-icons'

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    CandyListComponent,
    CandyEditComponent,
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PresentListComponent,
    PresentDetailComponent,
    PresentNewComponent,
    PresentNewSelectCandyComponent,
  ],
  entryComponents: [ConfirmationDeleteComponent],
  imports: [
    CoreModule,
    CommonModule,
    AppRoutingModule,
    FontAwesomeModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    ReactiveFormsModule,
    ValidationModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIcons(
      faAngleDown,
      faAngleUp,
      faCopy,
      faEye,
      faFile,
      faPencilAlt,
      faPlus,
      faRubleSign,
      faTimes,
      faTrash
    )
  }
}
