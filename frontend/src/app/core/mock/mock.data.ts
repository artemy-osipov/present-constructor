import { ID } from '@datorama/akita'
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
      id: id,
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

  getCandy(id: ID): Candy | undefined {
    return this.candies.find((x) => x.id === id)
  }

  addCandy(candy: Candy): ID {
    const newId = Math.max(...this.candies.map((x) => x.id as number)) + 1
    candy.id = newId
    this.candies.push(candy)
    return newId
  }

  updateCandy(candy: Candy) {
    const index = this.candies.findIndex((x) => x.id === candy.id)
    this.candies[index] = candy
  }

  deleteCandy(id: ID) {
    this.candies = this.candies.filter((x) => x.id !== id)
  }

  getPresent(id: ID): Present | undefined {
    return this.presents.find((x) => x.id === id)
  }

  addPresent(present: Present) {
    const newId = Math.max(...this.presents.map((x) => x.id as number)) + 1
    present.id = newId
    this.presents.push(present)
    return newId
  }

  deletePresent(id: ID) {
    this.presents = this.presents.filter((x) => x.id !== id)
  }
}
