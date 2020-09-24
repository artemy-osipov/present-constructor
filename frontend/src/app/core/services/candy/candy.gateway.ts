import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

import { Candy } from 'app/core/models/candy.model'
import { environment } from 'environments/environment'

@Injectable({ providedIn: 'root' })
export class CandyGateway {
  private candyResource = environment.apiUrl + 'api/candies/'

  constructor(private http: HttpClient) {}

  add(candy: Candy): Observable<string> {
    return this.http
      .post<string>(this.candyResource, candy, { observe: 'response' })
      .pipe(
        map((resp) => {
          if (resp.body) {
            return resp.body
          } else {
            throw new Error('id is not returned')
          }
        })
      )
  }

  get(id: string): Observable<Candy> {
    return this.http.get<Candy>(this.candyResource + id)
  }

  list(): Observable<Candy[]> {
    return this.http.get<Candy[]>(this.candyResource)
  }

  update(candy: Candy): Observable<Object> {
    return this.http.put(this.candyResource + candy.id, candy)
  }

  delete(id: string): Observable<Object> {
    return this.http.delete(this.candyResource + id)
  }
}
