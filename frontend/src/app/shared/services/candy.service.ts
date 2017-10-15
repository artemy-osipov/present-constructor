import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Rx';

import { Candy } from 'app/shared/model/candy.model';
import { environment } from 'environments/environment';

@Injectable()
export class CandyService {
  candyHost = environment.apiUrl + '/api/candies/';

  constructor(private http: HttpClient) { }

  add(candy: Candy): Observable<string> {
    return this.http.post(this.candyHost, candy, { observe: 'response' })
      .map(resp => this.getIdFromLocation(resp.headers.get('Location'))
    );
  }

  private getIdFromLocation(location: string): string {
    if (location.search(this.candyHost) === 0) {
      return location.substring(this.candyHost.length);
    }
  }

  get(id: string): Observable<Candy> {
    return this.http.get(this.candyHost + id)
      .map(res => new Candy(res));
  }

  list(): Observable<Candy[]> {
    return this.http.get<Object[]>(this.candyHost)
      .map(res => res.map(data => new Candy(data)));
  }

  update(candy: Candy): Observable<Object> {
    return this.http.put(this.candyHost + candy.id, candy);
  }

  delete(candy: Candy): Observable<Object> {
    return this.http.delete(this.candyHost + candy.id);
  }
}
