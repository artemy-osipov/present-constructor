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
          candy: this.candies[this.randomInt(0, this.candies.length)],
          count: 1,
        },
        {
          candy: this.candies[this.randomInt(0, this.candies.length)],
          count: 3,
        },
        {
          candy: this.candies[this.randomInt(0, this.candies.length)],
          count: 10,
        },
      ],
    })
  }

  randomInt(min: number, max: number) {
    min = Math.ceil(min)
    max = Math.floor(max)
    return Math.floor(Math.random() * (max - min)) + min
  }

  uuid(): string {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (
      c
    ) {
      var r = (Math.random() * 16) | 0,
        v = c == 'x' ? r : (r & 0x3) | 0x8
      return v.toString(16)
    })
  }

  getCandy(id: Candy['id']): Candy | undefined {
    return this.candies.find((x) => x.id === id)
  }

  addCandy(candy: Candy): string {
    candy.id = this.uuid()
    this.candies.push(candy)
    return candy.id
  }

  updateCandy(candy: Candy) {
    const index = this.candies.findIndex((x) => x.id === candy.id)
    this.candies[index] = candy
  }

  deleteCandy(id: Candy['id']) {
    this.candies = this.candies.filter((x) => x.id !== id)
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
    this.presents = this.presents.filter((x) => x.id !== id)
  }
}
