import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'
import { ReactiveFormsModule } from '@angular/forms'

import { MatDialogModule } from '@angular/material/dialog'
import { MatExpansionModule } from '@angular/material/expansion'
import { FontModule } from './font.module'
import { LinkBorderComponent } from './components/link-border/link-border.component'
import { ValidationErrorComponent } from './validation/validation-error.component'
import { ValidationDirective } from './validation/validation.directive'
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
  imports: [CommonModule, FontModule, MatDialogModule, MatExpansionModule],
  exports: [
    // vendor
    CommonModule,
    ReactiveFormsModule,
    // material
    MatDialogModule,
    MatExpansionModule,
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