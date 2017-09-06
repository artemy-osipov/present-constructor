import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Candy } from 'app/shared/candy.model';

@Injectable()
export class CandyStore {
  candies: Candy[] = [];

  constructor(private http: HttpClient) { }

  get orderedCandies(): Candy[] {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  fetch(): void {
    this.http.get<Candy[]>('http://localhost:8080/candies')
      .subscribe(data => {
        this.candies = data;
      });
  }

  add(candy: Candy): void {
    this.candies.push(candy);
    // this.http.post('http://localhost:8080/candies', candy);
  }

  update(candy: Candy): void {
    this.candies = this.candies.map(c => c.id === candy.id ? candy : c);
    // this.http.put('http://localhost:8080/candies/' + candy.id, candy);
  }

  delete(candy: Candy): void {
    this.candies = this.candies.filter(c => c.id !== candy.id);
    // this.http.put('http://localhost:8080/candies/' + candy.id, candy);
  }
}
