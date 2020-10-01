import { Component, OnInit } from '@angular/core'
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms'
import { ActivatedRoute } from '@angular/router'

import { Candy } from 'app/core/api/candy.gateway'
import { Present } from 'app/features/presents/service/present.model'
import { PresentService } from 'app/features/presents/service/present.service'
import {
  NumberValidators,
  StringValidators,
} from 'app/core/utils'

@Component({
  selector: 'app-present-new',
  templateUrl: './present-new.component.html',
})
export class PresentNewComponent implements OnInit {
  form: FormGroup
  successAdd = false

  get sourceId(): string {
    return this.route.snapshot.params.source
  }

  get itemsForm(): FormArray {
    return this.form.get('items') as FormArray
  }

  get present(): Present {
    return new Present(this.form.value, [])
  }

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private presentService: PresentService
  ) {
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: [
        '',
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(2),
        ],
      ],
      items: fb.array([], Validators.required),
    })
  }

  ngOnInit() {
    if (this.sourceId) {
      this.presentService
        .getPresent(this.sourceId)
        .subscribe((present) =>
          present.items.forEach((item) => this.addItem(item.candy, item.count))
        )
    }
  }

  private buildItemForm(candy: Candy, count: number) {
    return this.fb.group({
      count: [
        count,
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(0),
        ],
      ],
      candy: this.fb.group(candy),
    })
  }

  addItem(candy: Candy, count?: number) {
    this.itemsForm.push(this.buildItemForm(candy, count || 1))
  }

  removeItem(candy: Candy) {
    const index = this.itemsForm.controls.findIndex((item) => {
      return item.value.candy.id === candy.id
    })

    this.itemsForm.removeAt(index)
  }

  onSubmit() {
    this.form.markAllAsTouched()
    if (this.form.valid) {
      this.add(this.present)
    }
  }

  private add(present: Present) {
    this.presentService.add(present as any).subscribe(() => {
      this.form.reset()
      this.form.controls['items'] = this.fb.array([])

      this.successAdd = true
      setTimeout(() => (this.successAdd = false), 5000)
    })
  }
}
