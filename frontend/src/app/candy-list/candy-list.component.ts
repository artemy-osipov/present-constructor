import { Component } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

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
      candies.push(new Candy('f2e35882-5168-4ca5-8925-dd5f137857eb', 'Название ' + i, 'Производитель ' + i, i, i));
    }

    return candies;
  }

  openEditForm(candy: Candy): boolean {
    const modalRef = this.modalService.open(CandyEditComponent);
    modalRef.componentInstance.candy = candy;

    return false;
  }
}
