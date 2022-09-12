import type { Candy } from '$lib/candy/candy.model'
import { candyRepository } from '$lib/candy/candy.repository'
import { toMap } from '$lib/utils/collection.utils'
import { firstValueFrom } from 'rxjs'
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
