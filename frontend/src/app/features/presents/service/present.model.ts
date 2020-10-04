import { Candy } from 'app/core/api/candy.gateway'
import { Present as PresentDTO } from 'app/core/api/present.gateway'
import { toMap } from 'app/core/utils'

export interface PresentItem {
  candy: Candy
  count: number
}

export class Present {
  id: string
  name: string
  price: number
  items: PresentItem[] = []

  constructor(src: PresentDTO, candies: Candy[]) {
    this.id = src.id
    this.name = src.name
    this.price = src.price

    const grouppedCandies = toMap(candies, (c) => c.id)
    this.items =
      src.items?.map(
        (item) =>
          <PresentItem>{
            candy: grouppedCandies.get(item.candyId),
            count: item.count,
          }
      ) || []
  }

  get cost(): number {
    return this.items
      .map((i) => i.candy.price * i.count)
      .reduce((a, b) => a + b, 0)
  }
}
