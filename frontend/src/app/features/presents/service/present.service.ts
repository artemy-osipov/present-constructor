import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map, switchMap } from 'rxjs/operators'

import {
  PresentGateway,
  Present as PresentDTO,
} from 'app/core/api/present.gateway'
import { CandyGateway, Filter } from 'app/core/api/candy.gateway'
import { Present } from './present.model'

@Injectable({ providedIn: 'root' })
export class PresentService {
  constructor(
    private presentGateway: PresentGateway,
    private candyGateway: CandyGateway
  ) {}

  getPresent(id: string): Observable<Present> {
    return this.presentGateway.get(id).pipe(switchMap(this.fetchCandies))
  }

  private fetchCandies(p: PresentDTO): Observable<Present> {
    const candiesFilter: Filter = {
      ids: p.items.map((i) => i.candyId),
    }
    return this.candyGateway
      .list(candiesFilter)
      .pipe(map((cs) => new Present(p, cs)))
  }

  add(present: PresentDTO): Observable<string> {
    return this.presentGateway.add(present).pipe(
      map((id) => {
        present.id = id
        return id
      })
    )
  }
}
