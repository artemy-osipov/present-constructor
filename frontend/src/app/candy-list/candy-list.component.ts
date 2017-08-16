import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Candy } from 'app/shared/candy.model';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css']
})
export class CandyListComponent {
  candies: Candy[];

  constructor(private modalService: NgbModal) {
    this.candies = this.generateCandies(20);
  }

  private generateCandies(count: number): Candy[] {
    const candies: Candy[] = [];

    for (let i = 1; i <= count; i++) {
      candies.push(new Candy(i.toString(), 'Название ' + i, 'Производитель ' + i, i, i));
    }

    return candies;
  }

  get orderedCandies() {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  openAddForm(candy: Candy) {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.initAddForm(candy);
    modalRef.result
      .then(added => this.onAdded(added))
      .catch(e => {});
  }

  private onAdded(added: Candy) {
    this.candies.push(added);
  }

  openUpdateForm(candy: Candy) {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.initUpdateForm(candy);
    modalRef.result
      .then(edited => this.onEdited(edited))
      .catch(e => {});
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
      .catch(e => {});
  }

  private onDeleted(deleted: Candy) {
    this.candies = this.candies.filter(c => c.id !== deleted.id);
  }
}
