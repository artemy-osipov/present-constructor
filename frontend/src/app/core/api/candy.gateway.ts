import { HttpClient, HttpParams } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'

import { environment } from 'environments/environment'

export interface Candy {
  id: string
  name: string
  firm: string
  price: number
  order: number
}

export type NewCandyRequest = Omit<Candy, 'id'>

export interface Filter {
  ids: string[]
}

@Injectable({ providedIn: 'root' })
export class CandyGateway {
  private candyResource = environment.apiUrl + 'api/candies/'

  constructor(private http: HttpClient) {}

  add(req: NewCandyRequest): Observable<string> {
    return this.http.post<string>(this.candyResource, req)
  }

  get(id: string): Observable<Candy> {
    return this.http.get<Candy>(this.candyResource + id)
  }

  list(filter?: Filter): Observable<Candy[]> {
    const params = new HttpParams()
    if (filter) {
      params.set('ids', filter.ids.join(','))
    }
    return this.http.get<Candy[]>(this.candyResource, { params: params })
  }

  update(candy: Candy): Observable<undefined> {
    return this.http.put<undefined>(this.candyResource + candy.id, candy)
  }

  delete(id: string): Observable<undefined> {
    return this.http.delete<undefined>(this.candyResource + id)
  }
}
