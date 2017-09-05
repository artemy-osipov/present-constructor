import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
  styleUrls: ['./present-new.component.css']
})
export class PresentNewComponent {
  form: FormGroup;
  candies: Candy[];
  present: Present;

  constructor(private fb: FormBuilder) {
    this.candies = this.generateCandies(20);
    this.present = new Present();
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, NumberValidators.positive, NumberValidators.maxFractionLength(2)]],
      items: fb.array([])
    });
  }

  private generateCandies(count: number): Candy[] {
    const candies: Candy[] = [];

    for (let i = 1; i <= count; i++) {
      candies.push(this.generateCandy(i));
    }

    return candies;
  }

  private generateCandy(i: number): Candy {
    return new Candy(i.toString(), 'Название ' + i, 'Производитель ' + i, i, i);
  }

  get orderedCandies() {
    return this.candies.sort((x, y) => x.order - y.order);
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
