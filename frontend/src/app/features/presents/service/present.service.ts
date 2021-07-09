import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map, switchMap } from 'rxjs/operators'

import {
  PresentGateway,
  Present as PresentDTO,
} from 'app/core/api/present.gateway'
import { CandyGateway, Filter } from 'app/core/api/candy.gateway'
import { Present } from './present.model'

@Injectable()
export class PresentService {
  constructor(
    private presentGateway: PresentGateway,
    private candyGateway: CandyGateway
  ) {}

  getPresent(id: string): Observable<Present> {
    return this.presentGateway
      .get(id)
      .pipe(switchMap((p) => this.fetchCandies(p)))
  }

  private fetchCandies(present: PresentDTO): Observable<Present> {
    const candiesFilter: Filter = {
      ids: present.items.map((i) => i.candyId),
    }
    return this.candyGateway
      .list(candiesFilter)
      .pipe(map((cs) => new Present(present, cs)))
  }
}
