import { ComponentFactoryResolver, Directive, HostBinding, Optional, ViewContainerRef } from '@angular/core';
import { AbstractControlDirective, FormArrayName, NgControl } from '@angular/forms';

import { ValidationErrorComponent } from './validation-error/validation-error.component';

@Directive({
  selector: '[appFormControl]',
})
export class ValidationDirective {
  control: AbstractControlDirective;

  constructor(
    viewContainer: ViewContainerRef,
    resolver: ComponentFactoryResolver,
    @Optional() formControl: NgControl,
    @Optional() formArray: FormArrayName) {

    if (formControl !== null) {
      this.control = formControl;
    } else if (formArray !== null) {
      this.control = formArray;
    } else {
      throw new Error('no control to validate');
    }

    const factory = resolver.resolveComponentFactory(ValidationErrorComponent);
    const comp = viewContainer.createComponent(factory);
    comp.instance.control = this.control;
  }

  @HostBinding('class.is-valid')
  get valid(): boolean {
    return this.control.dirty && this.control.valid || false;
  }

  @HostBinding('class.is-invalid')
  get invalid(): boolean {
    return this.control.dirty && this.control.invalid || false;
  }
}
