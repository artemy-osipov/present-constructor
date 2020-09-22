import { Candy } from 'app/core/models/candy.model'
import { Present } from 'app/core/models/present.model'

export class Mock {
  candies: Candy[] = []
  presents: Present[] = []

  constructor() {
    for (let index = 0; index < 41; index++) {
      this.candies.push(this.newCandy(index))
    }
    for (let index = 0; index < 11; index++) {
      this.presents.push(this.newPresent(index))
    }
  }

  newCandy(id: number): Candy {
    return {
      id: id.toString(),
      name: 'some name ' + id,
      firm: 'some firm ' + id,
      price: 123.32,
      order: id,
    }
  }

  newPresent(id: number): Present {
    return new Present({
      id: id,
      name: 'some name ' + id,
      price: 123.32,
      date: new Date(),
      items: [
        {
          candy: this.newCandy(1),
          count: 1,
        },
        {
          candy: this.newCandy(3),
          count: 3,
        },
        {
          candy: this.newCandy(10),
          count: 10,
        },
      ],
    })
  }

  getCandy(id: string): Candy | undefined {
    return this.candies.find((x) => x.id === id)
  }

  addCandy(candy: Candy): string {
    const newId = (Math.max(...this.candies.map((x) => +x.id)) + 1).toString()
    candy.id = newId
    this.candies.push(candy)
    return newId
  }

  updateCandy(candy: Candy) {
    const index = this.candies.findIndex((x) => x.id === candy.id)
    this.candies[index] = candy
  }

  deleteCandy(id: string) {
    this.candies = this.candies.filter((x) => x.id !== id)
  }

  getPresent(id: string): Present | undefined {
    return this.presents.find((x) => x.id === id)
  }

  addPresent(present: Present) {
    const newId = (Math.max(...this.presents.map((x) => +x.id)) + 1).toString()
    present.id = newId
    this.presents.push(present)
    return newId
  }

  deletePresent(id: string) {
    this.presents = this.presents.filter((x) => x.id !== id)
  }
}
