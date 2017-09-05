import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Candy } from 'app/shared/candy.model';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html',
  styleUrls: ['./candy-edit.component.css']
})
export class CandyEditComponent {
  Action = Action;
  action: Action;
  modal: NgbActiveModal;
  form: FormGroup;
  candy: Candy;

  constructor(modal: NgbActiveModal, private fb: FormBuilder) {
    this.modal = modal;
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, NumberValidators.positive, NumberValidators.maxFractionLength(2)]],
      order: ['', Validators.required]
    });
  }

  initAddForm() {
    this.action = Action.Add;
  }

  initUpdateForm(candy: Candy) {
    this.action = Action.Update;
    this.candy = candy;
    this.form.setValue({
      name: candy.name,
      firm: candy.firm,
      price: candy.price,
      order: candy.order
    });
  }

  onSubmit() {
    if (this.form.valid) {
      switch (this.action) {
        case Action.Add:
          this.candy = this.addCandy();
          break;
        case Action.Update:
          this.candy = this.editCandy();
          break;
      }
      this.modal.close(this.candy);
    } else {
      for (const key in this.form.controls) {
        if (this.form.controls.hasOwnProperty(key)) {
          this.form.controls[key].markAsDirty();
        }
      }
    }
  }

  private addCandy(): Candy {
    const formModel = this.form.value;
    return new Candy('1', formModel.name.trim(), formModel.firm.trim(), formModel.price, formModel.order);
  }

  private editCandy(): Candy {
    const formModel = this.form.value;
    return new Candy(this.candy.id, formModel.name.trim(), formModel.firm.trim(), formModel.price, formModel.order);
  }
}

enum Action {
  Add, Update
}
