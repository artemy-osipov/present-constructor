import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'

import { environment } from 'environments/environment'

export interface PresentItem {
  candyId: string
  count: number
}

export interface Present {
  id: string
  name: string
  price: number
  date: string
  items: PresentItem[]
}

export type NewPresentRequest = Exclude<Present, 'id'>

@Injectable({ providedIn: 'root' })
export class PresentGateway {
  presentResource = environment.apiUrl + 'api/presents/'

  constructor(private http: HttpClient) {}

  add(req: NewPresentRequest): Observable<string> {
    return this.http.post<string>(this.presentResource, req)
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>(this.presentResource + id)
  }

  list(): Observable<Present[]> {
    return this.http.get<Present[]>(this.presentResource)
  }

  delete(id: string): Observable<unknown> {
    return this.http.delete(this.presentResource + id)
  }

  publicReportLocation(id: string) {
    return this.presentResource + id + '/public-report'
  }

  privateReportLocation(id: string) {
    return this.presentResource + id + '/private-report'
  }
}
