import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import { Candy } from 'app/shared/candy.model';

@Injectable()
export class CandyService {
  constructor(private http: HttpClient) { }

  list(): Observable<Candy[]> {
    return this.http.get('http://localhost:8080/candies');
  }

  add(candy: Candy): Observable<Candy> {
    return this.http.post('http://localhost:8080/candies', candy);
  }

  edit(candy: Candy): Observable<any> {
    return this.http.put('http://localhost:8080/candies/' + candy.id, candy);
  }

  generateCandies(count: number): Candy[] {
    const candies: Candy[] = [];

    for (let i = 1; i <= count; i++) {
      const candy = new Candy();
      candy.id = i.toString();
      candy.name = 'Название ' + i;
      candy.firm = 'Производитель ' + i;
      candy.price = i;
      candy.order = i;
      candies.push(candy);
    }

    return candies;
  }

  generateCandy(i: number): Candy {
    const candy = new Candy();
    candy.id = i.toString();
    candy.name = 'Название ' + i;
    candy.firm = 'Производитель ' + i;
    candy.price = i;
    candy.order = i;

    return candy;
  }
}
