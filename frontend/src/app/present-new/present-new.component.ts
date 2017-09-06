import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { CandyStore } from 'app/shared/services/candy.store';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
  styleUrls: ['./present-new.component.css']
})
export class PresentNewComponent {
  form: FormGroup;
  present: Present;

  constructor(private fb: FormBuilder, private candyStore: CandyStore) {
    this.candyStore.fetch();
    this.present = new Present();
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, NumberValidators.positive, NumberValidators.maxFractionLength(2)]],
      items: fb.array([])
    });
  }

  select(candy: Candy): void {
    if (this.present.hasCandy(candy)) {
      this.removeItem(candy);
    } else {
      this.addItem(candy);
    }
  }

  private removeItem(candy: Candy) {
    const index = this.present.items.findIndex(item => {
      return item.candy.id === candy.id;
    });

    const itemsForm = this.form.get('items') as FormArray;
    itemsForm.removeAt(index);

    this.present.items.splice(index, 1);
  }

  private addItem(candy: Candy) {
    const itemsForm = this.form.get('items') as FormArray;
    itemsForm.push(this.fb.group({
      count: ['', [Validators.required, NumberValidators.positive, NumberValidators.maxFractionLength(0)]]
    }));

    this.present.items.push(new PresentItem(candy, 1));
  }
}
