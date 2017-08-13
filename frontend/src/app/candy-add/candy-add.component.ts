import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Candy } from 'app/shared/candy.model';
import { StringValidators } from 'app/shared/string.validators';

@Component({
  selector: 'app-candy-add',
  templateUrl: './candy-add.component.html',
  styleUrls: ['./candy-add.component.css']
})
export class CandyAddComponent {
  modal: NgbActiveModal;
  form: FormGroup;

  constructor(modal: NgbActiveModal, private fb: FormBuilder) {
    this.modal = modal;
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{2})?$/)]]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const candy = this.prepareSaveCandy();
      this.modal.close(candy);
    }
  }

  private prepareSaveCandy(): Candy {
    const formModel = this.form.value;
    return new Candy('1', formModel.name.trim(), formModel.firm.trim(), formModel.price, 1);
  }
}
