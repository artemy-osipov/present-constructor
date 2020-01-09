import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

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
    public dialog: MatDialog,
    private candyStore: CandyStore
  ) {
    this.candyStore.fetch();
  }

  openAddForm() {
    this.router.navigate(['/candies/new']);
  }

  openUpdateForm(candy: Candy) {
    this.router.navigate(['/candies', candy.id]);
  }

  openDeleteForm(candy: Candy) {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.candyStore.delete(candy);
      }
    });
  }
}
