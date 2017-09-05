import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyService } from 'app/shared/services/candy.service';
import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Candy } from 'app/shared/candy.model';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css']
})
export class CandyListComponent {
  candies: Candy[] = [];

  constructor(private modalService: NgbModal, private candyService: CandyService) {
    this.candyService.list().subscribe(data => {
      this.candies = data;
    });
  }

  get orderedCandies() {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  openAddForm() {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.initAddForm();
    modalRef.result
      .then(added => this.onAdded(added))
      .catch(e => { });
  }

  private onAdded(added: Candy) {
    this.candies.push(added);
  }

  openUpdateForm(candy: Candy) {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.initUpdateForm(candy);
    modalRef.result
      .then(edited => this.onEdited(edited))
      .catch(e => { });
  }

  private onEdited(edited: Candy) {
    this.candies = this.candies.map(c => c.id === edited.id ? edited : c);
  }

  openDeleteForm(candy: Candy) {
    const modalRef = this.modalService.open(ConfirmationDeleteComponent);
    modalRef.result
      .then(res => {
        if (res) {
          this.onDeleted(candy);
        }
      })
      .catch(e => { });
  }

  private onDeleted(deleted: Candy) {
    this.candies = this.candies.filter(c => c.id !== deleted.id);
  }
}
