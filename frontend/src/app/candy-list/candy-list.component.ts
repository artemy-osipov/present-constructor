import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { Candy } from 'app/shared/candy.model';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { CandyService } from 'app/shared/services/candy.service';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css']
})
export class CandyListComponent {

  constructor(private modalService: NgbModal, private candyService: CandyService, private candyStore: CandyStore) {
    this.candyService.list().subscribe(
      candies => this.candyStore.candies = candies
    );
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
