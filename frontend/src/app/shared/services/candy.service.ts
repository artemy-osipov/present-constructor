import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Candy } from 'app/shared/model/candy.model';
import { environment } from 'environments/environment';

@Injectable()
export class CandyService {
  candyResource = environment.apiUrl + 'api/candies/';

  constructor(private http: HttpClient) { }

  add(candy: Candy): Observable<string> {
    return this.http.post(this.candyResource, candy, { observe: 'response' })
      .pipe(map(resp => this.getIdFromLocation(resp.headers.get('Location')))
    );
  }

  private getIdFromLocation(location: string): string {
    if (location.search(this.candyResource) === 0) {
      return location.substring(this.candyResource.length);
    }
  }

  get(id: string): Observable<Candy> {
    return this.http.get(this.candyResource + id)
      .pipe(map(res => new Candy(res)));
  }

  list(): Observable<Candy[]> {
    return this.http.get<Object[]>(this.candyResource)
      .pipe(map(res => res.map(data => new Candy(data))));
  }

  update(candy: Candy): Observable<Object> {
    return this.http.put(this.candyResource + candy.id, candy);
  }

  delete(candy: Candy): Observable<Object> {
    return this.http.delete(this.candyResource + candy.id);
  }
}
