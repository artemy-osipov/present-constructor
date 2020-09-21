import { FormGroup } from '@angular/forms'

export class FormHelper {
  static markFormContolsAsDirty(form: FormGroup) {
    for (const key in form.controls) {
      if (form.controls.hasOwnProperty(key)) {
        form.controls[key].markAsDirty()
      }
    }
  }
}
