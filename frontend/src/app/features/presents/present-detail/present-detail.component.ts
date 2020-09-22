import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { MatDialog } from '@angular/material/dialog'
import { ID } from '@datorama/akita'
import { Observable } from 'rxjs'

import { ConfirmationDeleteComponent } from 'app/shared/components/confirmation-delete/confirmation-delete.component'
import { Present } from 'app/core/models/present.model'
import { PresentQuery, PresentService } from 'app/core/services/present'

@Component({
  selector: 'app-present-detail',
  templateUrl: './present-detail.component.html',
})
export class PresentDetailComponent implements OnInit {
  present$: Observable<Present | undefined> = this.presentQuery.present(
    this.presentId
  )

  get presentId(): ID {
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
    private presentService: PresentService,
    private presentQuery: PresentQuery
  ) {}

  ngOnInit() {
    this.presentService.getPresent(this.presentId).subscribe()
  }

  openDeleteForm() {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent)

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.presentService.delete(this.presentId)
        this.router.navigate(['/presents'])
      }
    })
  }
}
