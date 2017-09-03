import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ValidationDirective } from './validation.directive';
import { ValidationErrorComponent } from './validation-error/validation-error.component';

@NgModule({
  declarations: [
    ValidationDirective,
    ValidationErrorComponent
  ],
  entryComponents: [
    ValidationErrorComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ValidationDirective
  ]
})
export class ValidationModule { }
