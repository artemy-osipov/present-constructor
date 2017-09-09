import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyStore } from 'app/shared/services/candy.store';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

import { Candy } from 'app/shared/candy.model';

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html',
  styleUrls: ['./candy-edit.component.css']
})
export class CandyEditComponent {
  Action = Action;
  action: Action;
  form: FormGroup;
  candy: Candy;

  constructor(private modal: NgbActiveModal, private fb: FormBuilder, private candyStore: CandyStore) {
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.min(1), NumberValidators.maxFractionLength(2)]],
      order: ['', Validators.required]
    });
  }

  initAddForm() {
    this.action = Action.Add;
  }

  initUpdateForm(candy: Candy) {
    this.action = Action.Update;
    this.candy = candy;
    this.form.patchValue(candy);
  }

  onSubmit() {
    if (this.form.valid) {
      switch (this.action) {
        case Action.Add:
          this.candyStore.add(this.candyFromForm());
          break;
        case Action.Update:
          this.candyStore.update(this.candyFromForm());
          break;
      }
      this.modal.close();
    } else {
      this.markFormContolsAsDirty(this.form);
    }
  }

  private markFormContolsAsDirty(form: FormGroup) {
    for (const key in form.controls) {
      if (form.controls.hasOwnProperty(key)) {
        form.controls[key].markAsDirty();
      }
    }
  }

  private candyFromForm(): Candy {
    const candy = new Candy(this.form.value);

    if (this.action === Action.Update) {
      candy.id = this.candy.id;
    }

    return candy;
  }
}

enum Action {
  Add, Update
}
