import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from 'app/app-routing.module';
import { ValidationDirective } from 'app/shared/validation.directive';

import { AppComponent } from 'app/app.component';
import { AboutComponent } from 'app/about/about.component';
import { HeaderComponent } from 'app/header/header.component';
import { CandyListComponent } from 'app/candy-list/candy-list.component';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { CandyDeleteComponent } from 'app/candy-delete/candy-delete.component';
import { CandyAddComponent } from 'app/candy-add/candy-add.component';

@NgModule({
  declarations: [
    ValidationDirective,
    AppComponent,
    AboutComponent,
    HeaderComponent,
    CandyListComponent,
    CandyEditComponent,
    CandyDeleteComponent,
    CandyAddComponent
  ],
  entryComponents: [
    CandyEditComponent,
    CandyDeleteComponent,
    CandyAddComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
