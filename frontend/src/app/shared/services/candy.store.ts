import { Injectable } from '@angular/core';
import { action, computed, observable } from 'mobx-angular';

import { Candy } from 'app/shared/model/candy.model';
import { CandyApi } from './candy.api.service';

@Injectable()
export class CandyStore {
  @observable candies: Candy[] = [];

  constructor(private candyApi: CandyApi) {
  }

  @action fetch() {
    this.candyApi.list().subscribe(
      candies => this.candies = candies
    );
  }

  @computed get orderedCandies(): Candy[] {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  @action add(candy: Candy) {
    this.candyApi.add(candy).subscribe(
      (id) => this.candyApi.get(id).subscribe(
        added => this.candies.push(added)
      )
    );
  }

  @action update(candy: Candy) {
    this.candyApi.update(candy).subscribe(
      () => this.candies = this.candies.map(c => c.id === candy.id ? candy : c)
    );
  }

  @action delete(candy: Candy) {
    this.candyApi.delete(candy).subscribe(
      () => this.candies = this.candies.filter(c => c.id !== candy.id)
    );
  }
}
