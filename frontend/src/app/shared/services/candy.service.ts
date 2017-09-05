import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { Candy } from 'app/shared/candy.model';

@Injectable()
export class CandyService {
  constructor(private http: HttpClient) { }

  list(): Observable<Candy[]> {
    return this.http.get('http://localhost:8080/candies');
  }

  private generateCandies(count: number): Candy[] {
    const candies: Candy[] = [];

    for (let i = 1; i <= count; i++) {
      candies.push(new Candy(i.toString(), 'Название ' + i, 'Производитель ' + i, i, i));
    }

    return candies;
  }
}
