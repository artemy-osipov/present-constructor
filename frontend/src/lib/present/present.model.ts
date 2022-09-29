import type { Candy } from '$lib/candy/candy.model'
import { toMap } from '$lib/utils/collection.utils'

export interface Present {
  id: string
  name: string
  price: number
  createDate: string
  items: PresentItem[]
}

export interface PresentItem {
  candyId: string
  count: number
}

export interface PresentView extends Omit<Present, 'items'> {
  items: PresentItemView[]
}

export interface PresentItemView {
  candy: Candy
  count: number
}

export function costByItemsView(items: PresentItemView[]): number {
  return items.map((i) => i.candy.price * i.count).reduce((a, b) => a + b, 0)
}

export function costByItems(items: PresentItem[], candies: Candy[]): number {
  const candyMap = toMap(candies, 'id')
  return items
    .map((i) => (candyMap.get(i.candyId)?.price || 0) * i.count)
    .reduce((a, b) => a + b, 0)
}
