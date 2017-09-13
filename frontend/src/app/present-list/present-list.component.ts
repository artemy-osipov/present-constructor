import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Present } from 'app/shared/model/present.model';
import { PresentService } from 'app/shared/services/present.service';
import { PresentStore } from 'app/shared/services/present.store';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html',
  styleUrls: ['./present-list.component.css']
})
export class PresentListComponent {

  constructor(private modalService: NgbModal, private presentService: PresentService, private presentStore: PresentStore) {
    this.presentService.list().subscribe(
      presents => this.presentStore.presents = presents
    );
  }

  openDeleteForm(present: Present) {
    const modalRef = this.modalService.open(ConfirmationDeleteComponent);
    modalRef.result
      .then(res => {
        if (res) {
          this.delete(present);
        }
      })
      .catch(e => { });
  }

  private delete(present: Present) {
    this.presentService.delete(present).subscribe(
      () => this.presentStore.delete(present)
    );
  }
}
