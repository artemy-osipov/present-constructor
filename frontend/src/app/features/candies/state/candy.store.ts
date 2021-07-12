import { Injectable } from '@angular/core'
import { EntityState, StoreConfig, EntityStore } from '@datorama/akita'

import { Candy } from 'app/core/api/candy.gateway'

export interface CandyState extends EntityState<Candy, Candy['id']> {}

@Injectable()
@StoreConfig({ name: 'candies' })
export class CandyStore extends EntityStore<CandyState, Candy> {
  constructor() {
    super()
  }
}