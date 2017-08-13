import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ReactiveFormsModule } from '@angular/forms';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppRoutingModule} from './app-routing.module';

import { AppComponent } from './app.component';
import { AboutComponent } from './about/about.component';
import { HeaderComponent } from './header/header.component';
import { CandyListComponent } from './candy-list/candy-list.component';
import { CandyEditComponent } from './candy-edit/candy-edit.component';
import { CandyDeleteComponent } from './candy-delete/candy-delete.component';
import { CandyAddComponent } from './candy-add/candy-add.component';

@NgModule({
  declarations: [
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
