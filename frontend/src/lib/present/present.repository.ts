import { createStore } from '@ngneat/elf'
import {
  deleteEntities,
  selectAllEntities,
  selectEntity,
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
import { filter, firstValueFrom, from, map, tap, type Observable } from 'rxjs'
import { presentGateway, type NewPresentRequest } from './present.api'
import type { Present } from './present.model'

const store = createStore(
  { name: 'presents' },
  withEntities<Present>(),
  withRequestsStatus<'list'>()
)
const trackRequestsStatus = createRequestsStatusOperator(store)

class PresentRepository {
  listPending = store.pipe(selectIsRequestPending('list'))
  presents: Observable<Present[]> = store.pipe(
    selectAllEntities(),
    map((cs) => cs.sort((a, b) => (a.createDate < b.createDate ? -1 : 1)))
  )

  present(id: Present['id']): Observable<Present | undefined> {
    return store.pipe(selectEntity(id))
  }

  async fetch(): Promise<void> {
    const loadingState = store.query(getRequestStatus('list'))
    if (loadingState.value === 'pending') {
      await firstValueFrom(this.listPending.pipe(filter((pending) => !pending)))
    } else if (loadingState.value !== 'success') {
      await firstValueFrom(
        from(presentGateway.list()).pipe(
          tap((presents) => this.setPresents(presents)),
          trackRequestsStatus('list')
        )
      )
    }
  }

  private setPresents(presents: Present[]) {
    store.update(setEntities(presents), updateRequestStatus('list', 'success'))
  }

  async add(req: NewPresentRequest): Promise<Present> {
    const presentId = await presentGateway.add(req)
    const present = await presentGateway.get(presentId)
    store.update(upsertEntities(present))
    return present
  }

  async delete(id: Present['id']): Promise<void> {
    await presentGateway.delete(id)
    store.update(deleteEntities(id))
  }
}

export const presentRepository = new PresentRepository()
