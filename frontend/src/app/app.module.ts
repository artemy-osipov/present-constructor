import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MatDialogModule } from '@angular/material/dialog';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MobxAngularModule } from 'mobx-angular';

import { AboutComponent } from 'app/about/about.component';
import { AppRoutingModule } from 'app/app-routing.module';
import { AppComponent } from 'app/app.component';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { HeaderComponent } from 'app/header/header.component';
import { LinkBorderComponent } from 'app/link-border/link-border.component';
import { PresentDetailComponent } from 'app/present-detail/present-detail.component';
import { PresentListComponent } from 'app/present-list/present-list.component';
import { PresentNewComponent } from 'app/present-new/present-new.component';
import { PresentNewSelectCandyComponent } from 'app/present-new/select-candy/present-new-select-candy.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { SecurityModule } from 'app/shared/security';
import { fakeBackendProvider } from 'app/shared/services/api-helper.service';
import { CandyApi } from 'app/shared/services/candy.api.service';
import { CandyStore } from 'app/shared/services/candy.store';
import { PresentApi } from 'app/shared/services/present.api.service';
import { PresentStore } from 'app/shared/services/present.store';
import { ValidationModule } from 'app/shared/validation';

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
  faTrash
} from '@fortawesome/free-solid-svg-icons';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    HeaderComponent,
    CandyListComponent,
    CandyEditComponent,
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PresentListComponent,
    PresentDetailComponent,
    PresentNewComponent,
    PresentNewSelectCandyComponent
  ],
  entryComponents: [CandyEditComponent, ConfirmationDeleteComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    MobxAngularModule,
    ReactiveFormsModule,
    SecurityModule,
    ValidationModule,
    NoopAnimationsModule
  ],
  providers: [CandyApi, CandyStore, PresentApi, PresentStore, fakeBackendProvider],
  bootstrap: [AppComponent]
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
    );
  }
}
