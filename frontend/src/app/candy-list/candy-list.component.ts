import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { CandyStore } from 'app/shared/services/candy.store';

import { Candy } from 'app/shared/candy.model';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css']
})
export class CandyListComponent {

  constructor(private modalService: NgbModal, private candyStore: CandyStore) {
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
          this.candyStore.delete(candy);
        }
      })
      .catch(e => { });
  }
}
