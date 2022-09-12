import { createState, Store } from '@ngneat/elf'
import {
  deleteEntities,
  getEntity,
  selectEntity,
  selectManyByPredicate,
  setEntities,
  upsertEntities,
  withEntities,
} from '@ngneat/elf-entities'
import {
  createRequestsStatusOperator,
  getRequestStatus,
  selectIsRequestPending,
  updateRequestStatus,
  withRequestsStatus,
} from '@ngneat/elf-requests'
import { filter, firstValueFrom, from, map, type Observable, tap } from 'rxjs'
import type { PresentItem } from '$lib/present/present.model'
import { candyGateway, type NewCandyRequest } from './candy.api'
import type { Candy } from './candy.model'

const { state, config } = createState(
  withEntities<Candy>(),
  withRequestsStatus<'list'>()
)
const store = new Store({ name: 'candies', state, config })
const trackRequestsStatus = createRequestsStatusOperator(store)

class CandyRepository {
  candiesPending = store.pipe(selectIsRequestPending('list'))
  activeCandies: Observable<Candy[]> = store.pipe(
    selectManyByPredicate((c) => c.active),
    map((cs) => cs.sort((a, b) => a.order - b.order))
  )

  candy(id: Candy['id']): Observable<Candy | undefined> {
    return store.pipe(selectEntity(id))
  }

  queryByItems(items: PresentItem[]): Candy[] {
    return items
      .map((i) => i.candyId)
      .map((id) => store.query(getEntity(id)))
      .filter((c): c is Candy => c !== undefined)
  }

  async fetch(): Promise<void> {
    const loadingState = store.query(getRequestStatus('list'))
    if (loadingState.value === 'pending') {
      await firstValueFrom(
        this.candiesPending.pipe(filter((pending) => !pending))
      )
    } else if (loadingState.value !== 'success') {
      await firstValueFrom(
        from(candyGateway.list()).pipe(
          tap((candies) => this.setCandies(candies)),
          trackRequestsStatus('list')
        )
      )
    }
  }

  private setCandies(candies: Candy[]) {
    store.update(setEntities(candies), updateRequestStatus('list', 'success'))
  }

  async add(req: NewCandyRequest): Promise<Candy> {
    const candyId = await candyGateway.add(req)
    const candy = await candyGateway.get(candyId)
    store.update(upsertEntities(candy))
    return candy
  }

  async update(candy: Candy): Promise<void> {
    await candyGateway.update(candy)
    store.update(upsertEntities(candy))
  }

  async delete(id: Candy['id']): Promise<void> {
    await candyGateway.delete(id)
    store.update(deleteEntities(id))
  }
}

export const candyRepository = new CandyRepository()
