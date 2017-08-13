import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Candy } from 'app/shared/candy.model';
import { StringValidators } from 'app/shared/string.validators';

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html',
  styleUrls: ['./candy-edit.component.css']
})
export class CandyEditComponent {
  modal: NgbActiveModal;
  form: FormGroup;
  candy: Candy;

  constructor(modal: NgbActiveModal, private fb: FormBuilder) {
    this.modal = modal;
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{2})?$/)]]
    });
  }

  initForm(candy: Candy) {
    this.candy = candy;
    this.form.setValue({
      name: candy.name,
      firm: candy.firm,
      price: candy.price
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.candy = this.prepareSaveCandy();
      this.modal.close(this.candy);
    } else {
      for (const key in this.form.controls) {
        if (this.form.controls.hasOwnProperty(key)) {
          this.form.controls[key].markAsDirty();
        }
      }
    }
  }

  private prepareSaveCandy(): Candy {
    const formModel = this.form.value;
    return new Candy(this.candy.id, formModel.name.trim(), formModel.firm.trim(), formModel.price, this.candy.order);
  }
}
