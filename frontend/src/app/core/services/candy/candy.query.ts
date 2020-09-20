import { Injectable } from '@angular/core';
import { QueryEntity, ID } from '@datorama/akita';
import { Observable } from 'rxjs';

import { CandyState, CandyStore } from './candy.store';
import { Candy } from 'app/core/models/candy.model';

@Injectable({ providedIn: 'root' })
export class CandyQuery extends QueryEntity<CandyState, Candy> {

  constructor(protected store: CandyStore) {
    super(store);
  }

  candy(id: ID): Observable<Candy | undefined> {
    return this.selectEntity(id);
  }

  sortedList(): Observable<Candy[]> {
    return this.selectAll({ sortBy: 'order' });
  }
}
