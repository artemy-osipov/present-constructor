import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'

import { ValidationErrorComponent } from './validation-error/validation-error.component'
import { ValidationDirective } from './validation.directive'

@NgModule({
  declarations: [ValidationDirective, ValidationErrorComponent],
  entryComponents: [ValidationErrorComponent],
  imports: [CommonModule],
  exports: [ValidationDirective],
})
export class ValidationModule {}
