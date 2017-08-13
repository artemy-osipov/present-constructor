import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CandyEditComponent } from 'app/candy-edit/candy-edit.component';
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

  openEditForm(candy: Candy) {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.initForm(candy);
    modalRef.result
      .then(edited => this.onEdited(edited))
      .catch(e => {});
  }

  private onEdited(edited: Candy) {
    this.candies = this.candies.map(c => c.id === edited.id ? edited : c);
  }
}
