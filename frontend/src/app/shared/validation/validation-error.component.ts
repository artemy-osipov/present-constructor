import { Component, Input } from '@angular/core'
import { AbstractControlDirective, ValidationErrors } from '@angular/forms'

@Component({
  selector: 'app-validation-error',
  templateUrl: './validation-error.component.html',
})
export class ValidationErrorComponent {
  @Input()
  control?: AbstractControlDirective

  get hasError(): boolean {
    return (
      (this.control !== undefined &&
        this.control.touched &&
        this.control.invalid) ||
      false
    )
  }

  get errors(): ValidationErrors {
    return Object.keys(this.control?.errors || {})
  }

  resolveErrorMessage(errorId: string): string {
    switch (errorId) {
      case 'notEmpty':
      case 'required':
        return 'Обязательно для заполнения'
      case 'min':
        return 'Меньше допустимого'
      case 'maxFractionLength':
        return 'Неверный формат'
      case 'maxLength':
        return 'Превышает максимальную длинуформат'
      default:
        return 'Неверное значение'
    }
  }
}
