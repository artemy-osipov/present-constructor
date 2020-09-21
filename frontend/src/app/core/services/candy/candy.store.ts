import { Injectable } from '@angular/core'
import { EntityState, StoreConfig, EntityStore } from '@datorama/akita'

import { Candy } from 'app/core/models/candy.model'

export interface CandyState extends EntityState<Candy> {}

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'candies' })
export class CandyStore extends EntityStore<CandyState, Candy> {
  constructor() {
    super({})
  }
}
