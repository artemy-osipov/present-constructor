import { NgModule } from '@angular/core'
import { RouterModule } from '@angular/router'
import { BrowserModule } from '@angular/platform-browser'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

import { environment } from 'environments/environment'
import { fakeBackendProvider } from 'app/core/mock/mock.interceptor'
import { HttpClientModule } from '@angular/common/http'
import { HeaderComponent } from './layout/header/header.component'

@NgModule({
  declarations: [HeaderComponent],
  imports: [
    // vendor
    BrowserModule,
    NoopAnimationsModule,
    RouterModule,
    HttpClientModule,
  ],
  exports: [HeaderComponent],
  providers: [environment.mock ? fakeBackendProvider : []],
})
export class CoreModule {}
