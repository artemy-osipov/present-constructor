import { firstValueFrom } from 'rxjs'
import { candyGateway } from '$lib/api/candy.api'
import { toMap } from '$lib/utils/collection.utils'
import type { Candy } from './candy.model'
import type { Present, PresentItemView, PresentView } from './present.model'
import { presentRepository } from './present.repository'

export async function fetchPresentView(
  id: Present['id']
): Promise<PresentView | undefined> {
  await presentRepository.fetch()
  const present = await firstValueFrom(presentRepository.present(id))

  if (present) {
    const candies = await fetchPresentCandies(present)
    return createView(present, candies)
  }
}

async function fetchPresentCandies(present: Present): Promise<Candy[]> {
  const candyIds = present.items.map((i) => i.candyId)
  return await candyGateway.list({ ids: candyIds })
}

function createView(present: Present, candies: Candy[]): PresentView {
  const groupedCandies = toMap(candies, 'id')
  return {
    ...present,
    items:
      present.items.map(
        (item) =>
          <PresentItemView>{
            candy: groupedCandies.get(item.candyId),
            count: item.count,
          }
      ) || [],
  }
}
