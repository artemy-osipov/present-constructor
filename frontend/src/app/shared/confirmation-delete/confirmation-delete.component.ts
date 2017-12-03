import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirmation-delete',
  templateUrl: './confirmation-delete.component.html'
})
export class ConfirmationDeleteComponent {

  constructor(public modal: NgbActiveModal) {
  }

  delete() {
    this.modal.close(true);
  }
}
