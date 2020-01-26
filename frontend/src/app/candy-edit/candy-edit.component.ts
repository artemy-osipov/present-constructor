import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

import { ConfirmationDeleteComponent } from 'app/confirmation-delete/confirmation-delete.component';
import { Candy } from 'app/shared/model/candy.model';
import { CandyStore } from 'app/shared/services/candy.store';
import {
  FormHelper,
  NumberValidators,
  StringValidators
} from 'app/shared/validation';

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html'
})
export class CandyEditComponent {
  form: FormGroup;
  editedCandy?: Candy;

  constructor(
    fb: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private candyStore: CandyStore
  ) {
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: [
        '',
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(2)
        ]
      ],
      order: ['', Validators.required]
    });

    candyStore.fetch();
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editedCandy = candyStore.candy(params['id']);

        if (this.editedCandy) {
          this.form.patchValue(this.editedCandy);
        }
      }
    });
  }

  isEdit() {
    return this.editedCandy !== undefined;
  }

  onSubmit() {
    if (this.form.valid) {
      const candyFromForm = this.candyFromForm();

      if (this.editedCandy) {
        this.candyStore.update(candyFromForm);
      } else {
        this.candyStore.add(candyFromForm);
      }
      this.router.navigate(['/candies']);
    } else {
      FormHelper.markFormContolsAsDirty(this.form);
    }
  }

  openDeleteForm() {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result && this.editedCandy) {
        this.candyStore.delete(this.editedCandy);
        this.router.navigate(['/candies']);
      }
    });
  }

  private candyFromForm(): Candy {
    const candy = new Candy(this.form.value);

    if (this.editedCandy) {
      candy.id = this.editedCandy.id;
    }

    return candy;
  }
}
