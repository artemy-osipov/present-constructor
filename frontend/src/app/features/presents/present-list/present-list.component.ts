import { Component } from '@angular/core'
import { Observable } from 'rxjs'

import { PresentGateway } from 'app/core/api/present.gateway'
import { Present } from 'app/core/api/present.dto'

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html',
})
export class PresentListComponent {
  presents$: Observable<Present[]> = this.presentGateway.list()

  constructor(private presentGateway: PresentGateway) {}
}
