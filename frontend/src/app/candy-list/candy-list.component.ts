import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Candy } from 'app/shared/model/candy.model';
import { CandyApi } from 'app/shared/services/candy.api.service';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html'
})
export class CandyListComponent {

  constructor(private modalService: NgbModal, private candyService: CandyApi, private candyStore: CandyStore) {
    this.candyStore.fetch();
  }

  openAddForm() {
    const modal = this.modalService.open(CandyEditComponent);
    modal.componentInstance.initAddForm();
  }

  openUpdateForm(candy: Candy) {
    const modal = this.modalService.open(CandyEditComponent);
    modal.componentInstance.initUpdateForm(candy);
  }

  openDeleteForm(candy: Candy) {
    const modal = this.modalService.open(ConfirmationDeleteComponent);
    modal.result
      .then(res => {
        if (res) {
          this.delete(candy);
        }
      })
      .catch(e => { });
  }

  private delete(candy: Candy) {
    this.candyService.delete(candy).subscribe(
      () => this.candyStore.delete(candy)
    );
  }
}
