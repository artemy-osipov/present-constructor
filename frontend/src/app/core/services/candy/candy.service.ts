import { Injectable } from '@angular/core'
import { ID } from '@datorama/akita'
import { Observable } from 'rxjs'
import { tap, map } from 'rxjs/operators'

import { Candy } from 'app/core/models/candy.model'
import { CandyStore } from './candy.store'
import { CandyGateway } from './candy.gateway'

@Injectable({ providedIn: 'root' })
export class CandyService {
  constructor(private store: CandyStore, private gateway: CandyGateway) {}

  list(): Observable<Candy[]> {
    return this.gateway.list().pipe(tap((candies) => this.store.set(candies)))
  }

  getCandy(id: ID): Observable<Candy> {
    return this.gateway
      .get(id)
      .pipe(tap((candy) => this.store.upsert(id, candy)))
  }

  add(candy: Candy): Observable<Candy> {
    return this.gateway.add(candy).pipe(
      map((id) => {
        candy.id = id
        this.store.upsert(id, candy)

        return candy
      })
    )
  }

  update(candy: Candy): Observable<Object> {
    return this.gateway
      .update(candy)
      .pipe(tap((_) => this.store.update(candy.id, candy)))
  }

  delete(id: ID): Observable<Object> {
    return this.gateway.delete(id).pipe(tap((_) => this.store.remove(id)))
  }
}
