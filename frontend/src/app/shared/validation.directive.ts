import { Directive, HostBinding } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
    selector: '[appFormControl]'
})
export class ValidationDirective {
    constructor(public control: NgControl) { }

    @HostBinding('class.is-valid')
    get valid(): boolean {
        return this.control.dirty && this.control.valid;
    }

    @HostBinding('class.is-invalid')
    get invalid(): boolean {
        return this.control.dirty && this.control.invalid;
    }
}
