import { AbstractControl, ValidatorFn } from '@angular/forms';

export class StringValidators {

  static notEmpty(control: AbstractControl): {[key: string]: boolean} {
    if (control.value === null || control.value.trim().length === 0) {
      return { notEmpty: true };
    }

    return null;
  }

  static maxLength(maxLength: number): ValidatorFn {
    return (control: AbstractControl): {[key: string]: boolean} => {
      if (control.value !== null && control.value.trim().length > maxLength) {
        return { maxLength: true };
      }

      return null;
    };
  }
}
