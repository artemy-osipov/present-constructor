import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Candy } from 'app/shared/model/candy.model';
import { Present } from 'app/shared/model/present.model';
import { PresentApi } from 'app/shared/services/present.api.service';
import {
  FormHelper,
  NumberValidators,
  StringValidators
} from 'app/shared/validation';

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
  styleUrls: ['./present-new.component.css']
})
export class PresentNewComponent {
  form: FormGroup;
  successAdd = false;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private presentApi: PresentApi
  ) {
    this.form = this.buildForm();

    this.route.params.subscribe(params => {
      const source = params['source'];
      if (source) {
        this.presentApi
          .get(source)
          .subscribe(present =>
            present.items.forEach(item => this.addItem(item.candy, item.count))
          );
      }
    });
  }

  get itemsForm(): FormArray {
    return this.form.get('items') as FormArray;
  }

  get present(): Present {
    return new Present(this.form.value);
  }

  private buildForm() {
    return this.fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: [
        '',
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(2)
        ]
      ],
      items: this.fb.array([], Validators.required)
    });
  }

  private buildItemForm(candy: Candy, count: number) {
    return this.fb.group({
      count: [
        count,
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(0)
        ]
      ],
      candy: this.fb.group(candy)
    });
  }

  addItem(candy: Candy, count?: number) {
    this.itemsForm.push(this.buildItemForm(candy, count || 1));
  }

  removeItem(candy: Candy) {
    const index = this.itemsForm.controls.findIndex(item => {
      return item.value.candy.id === candy.id;
    });

    this.itemsForm.removeAt(index);
  }

  onSubmit() {
    if (this.form.valid) {
      this.add(this.present);
    } else {
      FormHelper.markFormContolsAsDirty(this.form);
    }
  }

  private add(present: Present) {
    this.presentApi.add(present).subscribe(() => {
      this.form.reset();
      this.form.controls['items'] = this.fb.array([]);

      this.successAdd = true;
      setTimeout(() => (this.successAdd = false), 5000);
    });
  }
}
