import type { Candy } from '$lib/candy/candy.model'
import type { Present } from '$lib/present/present.model'

export type MockData = {
  candies: Candy[]
  presents: Present[]
}

export class Mock {
  readonly data: MockData

  get candies(): Candy[] {
    return this.data.candies
  }

  get presents(): Present[] {
    return this.data.presents
  }

  constructor(data?: MockData) {
    if (data) {
      this.data = data
    } else {
      this.data = {
        candies: [],
        presents: [],
      }
      for (let index = 0; index < 41; index += 1) {
        this.data.candies.push(this.newCandy(index))
      }
      for (let index = 0; index < 11; index += 1) {
        this.data.presents.push(this.newPresent(index))
      }
    }
  }

  newCandy(id: number): Candy {
    return {
      id: id.toString(),
      name: `some name ${id}`,
      firm: `some firm ${id}`,
      price: 123.32,
      grams: 10.5,
      order: id,
      active: true,
    }
  }

  newPresent(id: number): Present {
    return {
      id: id.toString(),
      name: `some name ${id}`,
      price: 123.32,
      createDate: new Date().toString(),
      items: [
        {
          candyId: this.candies[1].id,
          count: 1,
        },
        {
          candyId: this.candies[2].id,
          count: 3,
        },
        {
          candyId: this.candies[3].id,
          count: 10,
        },
      ],
    }
  }

  randomInt(min: number, max: number) {
    return Math.floor(Math.random() * (max - min)) + min
  }

  uuid(): string {
    return crypto.randomUUID()
  }

  getCandy(id: Candy['id']): Candy | undefined {
    return this.candies.find((x) => x.id === id)
  }

  addCandy(candy: Candy): string {
    candy.id = this.uuid()
    candy.active = true
    this.candies.push(candy)
    return candy.id
  }

  updateCandy(candy: Candy) {
    const index = this.candies.findIndex((x) => x.id === candy.id)
    this.candies[index] = candy
  }

  deleteCandy(id: Candy['id']) {
    this.data.candies = this.candies.filter((x) => x.id !== id)
  }

  getPresent(id: Present['id']): Present | undefined {
    return this.presents.find((x) => x.id === id)
  }

  addPresent(present: Present) {
    present.id = this.uuid()
    this.presents.push(present)
    return present.id
  }

  deletePresent(id: Present['id']) {
    this.data.presents = this.presents.filter((x) => x.id !== id)
  }
}
