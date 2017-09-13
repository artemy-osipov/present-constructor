import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Candy } from 'app/shared/model/candy.model';
import { Present, PresentItem } from 'app/shared/model/present.model';
import { PresentService } from 'app/shared/services/present.service';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
  styleUrls: ['./present-new.component.css']
})
export class PresentNewComponent {
  form: FormGroup;
  itemsForm: FormArray;
  present = new Present();
  successAdd = false;

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private presentService: PresentService) {
    this.form = this.buildForm();
    this.itemsForm = this.form.get('items') as FormArray;

    this.route.params.subscribe(params => {
      const source = params['source'];
      if (source) {
        this.presentService.get(source).subscribe(
          present => present.items.forEach(item => this.addItem(item.candy, item.count))
        );
      }
    });
  }

  private buildForm() {
    return this.fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.min(1), NumberValidators.maxFractionLength(2)]],
      items: this.fb.array([], Validators.required)
    });
  }

  private buildItemForm(candy: Candy) {
    return this.fb.group({
      count: ['', [Validators.required, Validators.min(1), NumberValidators.maxFractionLength(0)]],
      candy: this.fb.group({
        id: [candy.id]
      })
    });
  }

  private addItem(candy: Candy, count?: number) {
    this.itemsForm.push(this.buildItemForm(candy));
    this.present.items.push(new PresentItem(candy, count || 1));
  }

  private removeItem(candy: Candy) {
    const index = this.present.items.findIndex(item => {
      return item.candy.id === candy.id;
    });

    this.itemsForm.removeAt(index);
    this.present.items.splice(index, 1);
  }

  onSubmit() {
    if (this.form.valid) {
      this.add(this.form.value);
    } else {
      this.markFormContolsAsDirty(this.form);
    }
  }

  private add(present: Present) {
    this.presentService.add(present).subscribe(
      () => {
        this.present = new Present();
        this.form.reset();
        this.form.controls['items'] = this.fb.array([]);

        this.successAdd = true;
        setTimeout(() => this.successAdd = false, 5000);
      }
    );
  }

  private markFormContolsAsDirty(form: FormGroup) {
    for (const key in form.controls) {
      if (form.controls.hasOwnProperty(key)) {
        form.controls[key].markAsDirty();
      }
    }
  }
}
