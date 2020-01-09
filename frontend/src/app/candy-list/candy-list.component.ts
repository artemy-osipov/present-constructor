import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Candy } from 'app/shared/model/candy.model';
import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html',
  styleUrls: ['./candy-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CandyListComponent {
  constructor(
    private router: Router,
    private modalService: NgbModal,
    private candyStore: CandyStore
  ) {
    this.candyStore.fetch();
  }

  openAddForm() {
    this.router.navigate(['/candies/new'])
  }

  openUpdateForm(candy: Candy) {
    this.router.navigate(['/candies', candy.id])
  }

  openDeleteForm(candy: Candy) {
    const modal = this.modalService.open(ConfirmationDeleteComponent);
    modal.result
      .then(res => {
        if (res) {
          this.candyStore.delete(candy);
        }
      })
      .catch(e => { });
  }
}
