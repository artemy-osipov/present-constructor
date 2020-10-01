import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { tap, map } from 'rxjs/operators'

import { CandyGateway } from 'app/core/api/candy.gateway'
import { Candy } from 'app/core/api/candy.dto'
import { CandyStore } from './candy.store'

@Injectable({ providedIn: 'root' })
export class CandyService {
  constructor(private store: CandyStore, private gateway: CandyGateway) {}

  fetchList(): Observable<Candy[]> {
    return this.gateway.list().pipe(tap((candies) => this.store.set(candies)))
  }

  fetchCandy(id: Candy['id']): Observable<Candy> {
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

  delete(id: Candy['id']): Observable<Object> {
    return this.gateway.delete(id).pipe(tap((_) => this.store.remove(id)))
  }
}
