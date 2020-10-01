export interface PresentItem {
  candyId: string
  count: number
}

export interface Present {
  id: string
  name: string
  price: number
  date: string
  items: PresentItem[]
}
