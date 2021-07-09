import { Injectable } from '@angular/core'
import { QueryEntity } from '@datorama/akita'
import { Observable } from 'rxjs'
import { filter } from 'rxjs/operators'

import { Candy } from 'app/core/api/candy.gateway'
import { nonNullable } from 'app/core/utils'
import { CandyState, CandyStore } from './candy.store'

@Injectable()
export class CandyQuery extends QueryEntity<CandyState, Candy> {
  constructor(protected store: CandyStore) {
    super(store)
  }

  sortedList(): Observable<Candy[]> {
    return this.selectAll({ sortBy: 'order' })
  }

  candy(id: Candy['id']): Observable<Candy> {
    return this.selectEntity(id).pipe(filter(nonNullable))
  }
}
