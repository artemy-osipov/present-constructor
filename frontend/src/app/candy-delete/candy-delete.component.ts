import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-candy-delete',
  templateUrl: './candy-delete.component.html',
  styleUrls: ['./candy-delete.component.css']
})
export class CandyDeleteComponent {

  constructor(public modal: NgbActiveModal) {
  }

  delete() {
    this.modal.close(true);
  }
}
