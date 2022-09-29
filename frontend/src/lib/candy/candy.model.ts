export interface Candy {
  id: string
  name: string
  firm: string
  price: number
  grams?: number
  order: number
  active: boolean
}

export function formatView(candy: Candy): string {
  let view = candy.name
  if (candy.grams != null) {
    view += `, ${candy.grams} г.`
  }
  return view
}
