import { Injectable } from '@angular/core';
import { action, computed, observable } from 'mobx-angular';

import { Candy } from 'app/shared/model/candy.model';

@Injectable()
export class CandyStore {
  @observable candies: Candy[] = [];

  @computed get orderedCandies(): Candy[] {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  @action add(candy: Candy) {
    this.candies.push(candy);
  }

  @action update(candy: Candy) {
    this.candies = this.candies.map(c => c.id === candy.id ? candy : c);
  }

  @action delete(candy: Candy) {
    this.candies = this.candies.filter(c => c.id !== candy.id);
  }
}
