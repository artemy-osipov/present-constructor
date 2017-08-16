import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
      presents.push(new Present(i.toString(), 'Название ' + i, i, new Date(2017, 1, 1, 1, 1, 1, i), []));
    }

    return presents;
  }

  get orderedPresents() {
    return this.presents.sort((x, y) => x.date.getTime() - y.date.getTime());
  }

  openDeleteForm(present: Present) {
    const modalRef = this.modalService.open(null);
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
