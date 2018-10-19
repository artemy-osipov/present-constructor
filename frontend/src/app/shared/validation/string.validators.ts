import { ValidatorFn } from '@angular/forms';

export class StringValidators {

  static notEmpty(): ValidatorFn {
    return control => {
      if (control.value === null || control.value.trim().length === 0) {
        return { notEmpty: true };
      }

      return null;
    };
  }

  static maxLength(maxLength: number): ValidatorFn {
    return control => {
      if (control.value !== null && control.value.trim().length > maxLength) {
        return { maxLength: true };
      }

      return null;
    };
  }
}
