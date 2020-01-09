import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Present } from 'app/shared/model/present.model';
import { PresentStore } from 'app/shared/services/present.store';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PresentListComponent {
  constructor(
    public dialog: MatDialog,
    private presentStore: PresentStore
  ) {
    this.presentStore.fetch();
  }

  openDeleteForm(present: Present) {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.presentStore.delete(present);
      }
    });
  }
}
