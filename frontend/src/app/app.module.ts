import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from 'app/app-routing.module';
import { ValidationModule } from 'app/shared/validation/index';

import { AboutComponent } from 'app/about/about.component';
import { AppComponent } from 'app/app.component';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { HeaderComponent } from 'app/header/header.component';
import { LinkBorderComponent } from 'app/link-border/link-border.component';
import { PresentDetailComponent } from 'app/present-detail/present-detail.component';
import { PresentListComponent } from 'app/present-list/present-list.component';
import { PresentNewComponent } from 'app/present-new/present-new.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';

import { CandyStore } from 'app/shared/services/candy.store';

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
    PresentNewComponent
  ],
  entryComponents: [
    CandyEditComponent,
    ConfirmationDeleteComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    AppRoutingModule,
    ValidationModule
  ],
  providers: [
    CandyStore
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
