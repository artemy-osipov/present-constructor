import { Candy } from 'app/core/api/candy.gateway'
import { Present } from 'app/core/api/present.gateway'

import { environment } from 'environments/environment'

export type MockData = {
  candies: Candy[]
  presents: Present[]
}

export class Mock {
  data: MockData = {
    candies: [],
    presents: []
  }

  get candies(): Candy[] {
    return this.data.candies
  }

  get presents(): Present[] {
    return this.data.presents
  }

  constructor() {
    if (typeof environment.mock === 'object' && environment.mock !== null) {
      this.data = environment.mock
    } else {
      for (let index = 0; index < 41; index++) {
        this.data.candies.push(this.newCandy(index))
      }
      for (let index = 0; index < 11; index++) {
        this.data.presents.push(this.newPresent(index))
      }
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
    return {
      id: id.toString(),
      name: 'some name ' + id,
      price: 123.32,
      date: new Date().toString(),
      items: [
        {
          candyId: this.candies[this.randomInt(0, this.candies.length)].id,
          count: 1,
        },
        {
          candyId: this.candies[this.randomInt(0, this.candies.length)].id,
          count: 3,
        },
        {
          candyId: this.candies[this.randomInt(0, this.candies.length)].id,
          count: 10,
        },
      ],
    }
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
