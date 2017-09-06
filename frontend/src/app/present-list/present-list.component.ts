import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';

import { Present } from 'app/shared/present.model';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html',
  styleUrls: ['./present-list.component.css']
})
export class PresentListComponent {
  presents: Present[];

  constructor(private modalService: NgbModal) {
    this.presents = this.generatePresents(20);
  }

  private generatePresents(count: number): Present[] {
    const presents: Present[] = [];

    for (let i = 1; i <= count; i++) {
      presents.push(this.generatePresent(i));
    }

    return presents;
  }

  private generatePresent(i: number): Present {
    const present = new Present();
    present.id = i.toString();
    present.name = 'Название ' + i;
    present.price = i;
    present.date = new Date(2017, 1, 1, 1, 1, 1, i);

    return present;
  }

  get orderedPresents() {
    return this.presents.sort((x, y) => x.date.getTime() - y.date.getTime());
  }

  openDeleteForm(present: Present) {
    const modalRef = this.modalService.open(ConfirmationDeleteComponent);
    modalRef.result
      .then(res => {
        if (res) {
          this.onDeleted(present);
        }
      })
      .catch(e => { });
  }

  private onDeleted(deleted: Present) {
    this.presents = this.presents.filter(c => c.id !== deleted.id);
  }
}
