import { Component } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { MatDialog } from '@angular/material/dialog'
import { Observable } from 'rxjs'
import { filter, switchMap } from 'rxjs/operators'

import { ConfirmationDeleteComponent } from 'app/shared/components/confirmation-delete/confirmation-delete.component'
import { Present } from 'app/features/presents/service/present.model'
import { PresentService } from 'app/features/presents/service/present.service'

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html',
})
export class PresentDetailComponent {
  present$: Observable<Present> = this.presentService.getPresent(
    this.presentId
  )

  get presentId(): string {
    return this.route.snapshot.params.id
  }

  get publicReportLink(): string {
    return this.presentService.publicReportLocation(this.presentId)
  }

  get privateReportLink(): string {
    return this.presentService.privateReportLocation(this.presentId)
  }

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private presentService: PresentService
  ) {}

  openDeleteForm() {
    this.dialog
      .open(ConfirmationDeleteComponent)
      .afterClosed()
      .pipe(
        filter(Boolean),
        switchMap((_) => this.presentService.delete(this.presentId))
      )
      .subscribe((_) => this.router.navigate(['/presents']))
  }
}
