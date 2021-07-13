import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'
import { ReactiveFormsModule } from '@angular/forms'

import { ScrollingModule } from '@angular/cdk/scrolling'
import { MatDialogModule } from '@angular/material/dialog'
import { MatExpansionModule } from '@angular/material/expansion'
import { FontModule } from './font.module'
import { LinkBorderComponent } from './components/link-border/link-border.component'
import { ScrollNavComponent } from './components/scroll-nav/scroll-nav.component'
import { ValidationErrorComponent } from './validation/validation-error.component'
import { ValidationDirective } from './validation/validation.directive'
import { ConfirmationDeleteComponent } from './components/confirmation-delete/confirmation-delete.component'
import { PricePipe } from './pipes/price.pipe'

@NgModule({
  declarations: [
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PricePipe,
    ScrollNavComponent,
    ValidationDirective,
    ValidationErrorComponent,
  ],
  imports: [
    CommonModule,
    FontModule,
    MatDialogModule,
    MatExpansionModule,
    ScrollingModule,
  ],
  exports: [
    // vendor
    CommonModule,
    ReactiveFormsModule,
    // material
    MatDialogModule,
    MatExpansionModule,
    ScrollingModule,
    // local
    FontModule,
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PricePipe,
    ScrollNavComponent,
    ValidationDirective,
    ValidationErrorComponent,
  ],
})
export class SharedModule {}
