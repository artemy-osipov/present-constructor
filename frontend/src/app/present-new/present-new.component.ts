import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Candy } from 'app/shared/candy.model';
import { Present, PresentItem } from 'app/shared/present.model';
import { CandyService } from 'app/shared/services/candy.service';
import { CandyStore } from 'app/shared/services/candy.store';
import { PresentService } from 'app/shared/services/present.service';
import { NumberValidators, StringValidators } from 'app/shared/validation/index';

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
  styleUrls: ['./present-new.component.css']
})
export class PresentNewComponent {
  form: FormGroup;
  present = new Present();

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private presentService: PresentService,
    private candyService: CandyService,
    private candyStore: CandyStore) {
    this.candyService.list().subscribe(
      candies => this.candyStore.candies = candies
    );

    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.min(1), NumberValidators.maxFractionLength(2)]],
      items: fb.array([])
    });

    this.route.params.subscribe(params => {
      const source = params['source'];
      if (source) {
        this.presentService.get(source).subscribe(
          present => present.items.forEach(item => this.addItem(item.candy, item.count))
        );
      }
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

  private addItem(candy: Candy, count?: number) {
    const itemsForm = this.form.get('items') as FormArray;
    itemsForm.push(this.fb.group({
      count: ['', [Validators.required, Validators.min(1), NumberValidators.maxFractionLength(0)]]
    }));

    this.present.items.push(new PresentItem(candy, count || 1));
  }
}
