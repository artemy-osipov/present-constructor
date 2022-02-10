import { firstValueFrom } from 'rxjs'
import { toMap } from '$lib/utils/collection.utils'
import type { Candy } from './candy.model'
import type { Present, PresentItemView, PresentView } from './present.model'
import { presentRepository } from './present.repository'
import { candyRepository } from './candy.repository'

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
  await candyRepository.fetch()
  return candyRepository.queryByItems(present.items)
}

function createView(present: Present, candies: Candy[]): PresentView {
  const groupedCandies = toMap(candies, 'id')
  const items: PresentItemView[] = present.items
    .map(
      (item) =>
        <PresentItemView>{
          candy: groupedCandies.get(item.candyId),
          count: item.count,
        }
    )
    .sort((a, b) => a.candy.order - b.candy.order)
  return {
    ...present,
    items,
  }
}
