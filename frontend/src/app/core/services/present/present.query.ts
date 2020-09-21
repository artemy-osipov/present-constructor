import { Injectable } from '@angular/core'
import { QueryEntity, ID } from '@datorama/akita'
import { Observable } from 'rxjs'

import { PresentState, PresentStore } from './present.store'
import { Present } from 'app/core/models/present.model'

@Injectable({ providedIn: 'root' })
export class PresentQuery extends QueryEntity<PresentState, Present> {
  constructor(protected store: PresentStore) {
    super(store)
  }

  present(id: ID): Observable<Present | undefined> {
    return this.selectEntity(id)
  }

  sortedList(): Observable<Present[]> {
    return this.selectAll({ sortBy: 'date' })
  }
}
