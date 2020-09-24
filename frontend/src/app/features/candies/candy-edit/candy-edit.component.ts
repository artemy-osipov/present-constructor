import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router'
import { MatDialog } from '@angular/material/dialog'
import { Observable } from 'rxjs'

import { ConfirmationDeleteComponent } from 'app/shared/components/confirmation-delete/confirmation-delete.component'
import { Candy } from 'app/core/models/candy.model'
import { CandyQuery, CandyService } from 'app/core/services/candy'
import { NumberValidators, StringValidators } from 'app/core/utils'

@Component({
  selector: 'app-candy-edit',
  templateUrl: './candy-edit.component.html',
})
export class CandyEditComponent implements OnInit {
  form: FormGroup
  editedCandy$: Observable<Candy> = this.candyQuery.candy(this.candyId)

  get candyId(): string {
    return this.route.snapshot.params.id
  }

  get isEdit(): boolean {
    return !!this.candyId
  }

  constructor(
    fb: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private candyService: CandyService,
    private candyQuery: CandyQuery
  ) {
    this.form = fb.group({
      name: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      firm: ['', [StringValidators.notEmpty, StringValidators.maxLength(50)]],
      price: [
        '',
        [
          Validators.required,
          Validators.min(1),
          NumberValidators.maxFractionLength(2),
        ],
      ],
      order: ['', Validators.required],
    })
  }

  ngOnInit() {
    if (this.isEdit) {
      if (!this.candyQuery.hasEntity(this.candyId)) {
        this.candyService.fetchCandy(this.candyId).subscribe()
      }
      this.editedCandy$.subscribe((candy) => {
        this.form.patchValue(candy)
        this.form.markAllAsTouched()
      })
    }
  }

  onSubmit() {
    this.form.markAllAsTouched()
    if (this.form.valid) {
      const candyFromForm = this.candyFromForm()

      if (this.isEdit) {
        this.candyService.update(candyFromForm)
      } else {
        this.candyService.add(candyFromForm)
      }
      this.router.navigate(['/candies'])
    }
  }

  openDeleteForm() {
    const dialogRef = this.dialog.open(ConfirmationDeleteComponent)

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.candyService.delete(this.candyId)
        this.router.navigate(['/candies'])
      }
    })
  }

  private candyFromForm(): Candy {
    const candy = this.form.value
    candy.id = this.candyId

    return candy
  }
}
