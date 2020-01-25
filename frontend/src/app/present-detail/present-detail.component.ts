import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

import { ConfirmationDeleteComponent } from 'app/shared/confirmation-delete/confirmation-delete.component';
import { Present } from 'app/shared/model/present.model';
import { PresentApi } from 'app/shared/services/present.api.service';
import { PresentStore } from 'app/shared/services/present.store';

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html'
})
export class PresentDetailComponent {
  present: Present = new Present({});

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private presentApi: PresentApi,
    private presentStore: PresentStore,
  ) {
    this.route.params.subscribe(params => {
      this.presentApi
        .get(params['id'])
        .subscribe(present => (this.present = present));
    });
  }

  openDeleteForm(present: Present) {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.presentStore.delete(present);
        this.router.navigate(['/presents']);
      }
    });
  }
}
