import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { PresentStore } from 'app/shared/services/present.store';

import { Present } from 'app/shared/present.model';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html',
  styleUrls: ['./present-list.component.css']
})
export class PresentListComponent {

  constructor(private modalService: NgbModal, private presentStore: PresentStore) {
    this.presentStore.fetch();
  }

  openDeleteForm(present: Present) {
    const modalRef = this.modalService.open(ConfirmationDeleteComponent);
    modalRef.result
      .then(res => {
        if (res) {
          this.presentStore.delete(present);
        }
      })
      .catch(e => { });
  }
}
