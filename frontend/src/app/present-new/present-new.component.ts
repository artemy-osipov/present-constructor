import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';
import { StringValidators } from 'app/shared/string.validators';

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
    this.present = this.generatePresent();
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{2})?$/)]],
      count: ['', [Validators.required, Validators.pattern(/^[1-9]\d*$/)]]
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

  private generatePresent(): Present {
    return new Present('1', 'name', 123.12, new Date(), []);
  }

  get orderedCandies() {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  select(candy: Candy): void {
    if (this.present.hasCandy(candy)) {
      this.present.items = this.present.items.filter(item => {
        return item.candy.id !== candy.id;
      });
    } else {
      this.present.items.push(new PresentItem(candy, 1));
    }
  }
}
