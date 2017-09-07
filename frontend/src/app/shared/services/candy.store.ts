import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';

import { Candy } from 'app/shared/candy.model';

@Injectable()
export class CandyStore {
  candies: Candy[] = [];

  constructor(private http: HttpClient) { }

  get orderedCandies(): Candy[] {
    return this.candies.sort((x, y) => x.order - y.order);
  }

  fetch(): Observable<Candy[]> {
    const res: Observable<Candy[]> = this.http.get<Candy[]>('http://localhost:8080/candies');
    res.subscribe(data => {
      this.candies = data;
    });

    return res;
  }

  add(candy: Candy): Observable<Candy> {
    const res: Subject<Candy> = new Subject();
    this.http.post('http://localhost:8080/candies', candy, { observe: 'response' })
      .subscribe(resp => {
        const added: Observable<Candy> = this.fetchAdded(resp.headers.get('Location'));
        added.subscribe(res);
      },
      err => {
        res.error(err);
        res.complete();
      });

    return res;
  }

  private fetchAdded(location: string): Observable<Candy> {
    const res: Observable<Candy> = this.http.get<Candy>(location);
    res.subscribe(data => {
      this.candies.push(data);
    });

    return res;
  }

  update(candy: Candy): Observable<Object> {
    const res: Observable<Candy> = this.http.put('http://localhost:8080/candies/' + candy.id, candy);
    res.subscribe(data => {
      this.candies = this.candies.map(c => c.id === candy.id ? candy : c);
    });

    return res;
  }

  delete(candy: Candy): Observable<Object> {
    const res: Observable<Candy> = this.http.delete('http://localhost:8080/candies/' + candy.id);
    res.subscribe(data => {
      this.candies = this.candies.filter(c => c.id !== candy.id);
    });

    return res;
  }
}
