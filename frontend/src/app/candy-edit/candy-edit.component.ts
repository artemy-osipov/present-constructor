import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Candy } from 'app/shared/model/candy.model';
import { CandyApi } from 'app/shared/services/candy.api.service';
import { CandyStore } from 'app/shared/services/candy.store';
import { FormHelper, NumberValidators, StringValidators } from 'app/shared/validation';

enum Action {
  Add, Update
}

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html'
})
export class CandyEditComponent {
  Action = Action;
  action?: Action;
  form: FormGroup;
  candy?: Candy;

  constructor(public modal: NgbActiveModal, private fb: FormBuilder, private candyService: CandyApi, private candyStore: CandyStore) {
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty(), StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty(), StringValidators.maxLength(50)]],
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
          this.add(this.candyFromForm());
          break;
        case Action.Update:
          this.update(this.candyFromForm());
          break;
      }
      this.modal.close();
    } else {
      FormHelper.markFormContolsAsDirty(this.form);
    }
  }

  private candyFromForm(): Candy {
    const candy = new Candy(this.form.value);

    if (this.action === Action.Update && this.candy) {
      candy.id = this.candy.id;
    }

    return candy;
  }

  private add(candy: Candy) {
    this.candyService.add(candy).subscribe(
      (id) => this.candyService.get(id).subscribe(
        added => this.candyStore.add(added)
      )
    );
  }

  private update(candy: Candy) {
    this.candyService.update(candy).subscribe(
      () => this.candyStore.update(candy)
    );
  }
}
