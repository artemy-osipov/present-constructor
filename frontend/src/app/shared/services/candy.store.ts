import { Injectable } from '@angular/core';

import { Candy } from 'app/shared/model/candy.model';

@Injectable()
export class CandyStore {
  candies: Candy[] = [];

  get orderedCandies(): Candy[] {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  add(candy: Candy) {
    this.candies.push(candy);
  }

  update(candy: Candy) {
    this.candies = this.candies.map(c => c.id === candy.id ? candy : c);
  }

  delete(candy: Candy) {
    this.candies = this.candies.filter(c => c.id !== candy.id);
  }
}
