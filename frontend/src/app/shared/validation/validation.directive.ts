import { ComponentFactoryResolver, Directive, HostBinding, ViewContainerRef } from '@angular/core';
import { NgControl } from '@angular/forms';

import { ValidationErrorComponent } from './validation-error/validation-error.component';

@Directive({
    selector: '[appFormControl]'
})
export class ValidationDirective {
    constructor(public control: NgControl, private viewContainer: ViewContainerRef, private resolver: ComponentFactoryResolver) {
        const factory = resolver.resolveComponentFactory(ValidationErrorComponent);
        const comp = viewContainer.createComponent(factory);
        comp.instance.control = control;
    }

    @HostBinding('class.is-valid')
    get valid(): boolean {
        return this.control.dirty && this.control.valid;
    }

    @HostBinding('class.is-invalid')
    get invalid(): boolean {
        return this.control.dirty && this.control.invalid;
    }
}
