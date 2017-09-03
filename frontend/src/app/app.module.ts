import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from 'app/app-routing.module';
import { ValidationModule } from 'app/shared/validation/validation.module';

import { AppComponent } from 'app/app.component';
import { AboutComponent } from 'app/about/about.component';
import { HeaderComponent } from 'app/header/header.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { LinkBorderComponent } from 'app/link-border/link-border.component';
import { PresentListComponent } from 'app/present-list/present-list.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { PresentDetailComponent } from 'app/present-detail/present-detail.component';
import { PresentNewComponent } from 'app/present-new/present-new.component';

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
    HttpModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    AppRoutingModule,
    ValidationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
