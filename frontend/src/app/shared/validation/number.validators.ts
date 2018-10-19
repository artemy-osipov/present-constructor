import { ValidatorFn } from '@angular/forms';

export class NumberValidators {

  static maxFractionLength(maxFractionLength: number): ValidatorFn {
    return control => {
      if (control.value !== null && NumberValidators.getFractionLength(control.value) > maxFractionLength) {
        return { maxFractionLength: true };
      }

      return null;
    };
  }

  private static getFractionLength(n: number): number {
    return (n.toString().split('.')[1] || []).length;
  }
}
