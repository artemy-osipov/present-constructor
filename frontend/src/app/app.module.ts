import { NgModule } from '@angular/core'

import { CoreModule } from 'app/core/core.module'

import { AppRoutingModule } from 'app/app-routing.module'
import { AppComponent } from 'app/app.component'

@NgModule({
  declarations: [AppComponent],
  imports: [CoreModule, AppRoutingModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
