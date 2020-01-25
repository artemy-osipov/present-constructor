import { Component, Input, ViewEncapsulation } from '@angular/core';
import { AbstractControlDirective } from '@angular/forms';

@Component({
  selector: 'app-validation-error',
  templateUrl: './validation-error.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ValidationErrorComponent {
  @Input()
  control?: AbstractControlDirective;

  get hasError(): boolean {
    return (
      (this.control !== undefined &&
        this.control.invalid &&
        this.control.errors !== null) ||
      false
    );
  }
}
