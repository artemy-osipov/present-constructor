import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'
import { ReactiveFormsModule } from '@angular/forms'

import { FontModule } from './font.module'
import { LinkBorderComponent } from './components/link-border/link-border.component'
import { ValidationErrorComponent } from './validation/validation-error.component'
import { ValidationDirective } from './validation/validation.directive'
import { MatDialogModule } from '@angular/material/dialog'
import { ConfirmationDeleteComponent } from './components/confirmation-delete/confirmation-delete.component'
import { PricePipe } from './pipes/price.pipe'

@NgModule({
  declarations: [
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PricePipe,
    ValidationDirective,
    ValidationErrorComponent,
  ],
  imports: [CommonModule, FontModule, MatDialogModule],
  exports: [
    // vendor
    CommonModule,
    ReactiveFormsModule,
    // material
    MatDialogModule,
    // local
    FontModule,
    ConfirmationDeleteComponent,
    LinkBorderComponent,
    PricePipe,
    ValidationDirective,
    ValidationErrorComponent,
  ],
})
export class SharedModule {}
