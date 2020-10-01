import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

import { environment } from 'environments/environment'
import { Present } from './present.dto'

@Injectable({ providedIn: 'root' })
export class PresentGateway {
  presentResource = environment.apiUrl + 'api/presents/'

  constructor(private http: HttpClient) {}

  add(present: Present): Observable<string> {
    return this.http
      .post(this.presentResource, present, { observe: 'response' })
      .pipe(
        map((resp) => {
          if (resp.body) {
            return resp.body.toString()
          } else {
            throw new Error('id is not returned')
          }
        })
      )
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>(this.presentResource + id)
  }

  list(): Observable<Present[]> {
    return this.http.get<Present[]>(this.presentResource)
  }

  delete(id: string): Observable<Object> {
    return this.http.delete(this.presentResource + id)
  }

  publicReportLocation(id: string) {
    return this.presentResource + id + '/public-report'
  }

  privateReportLocation(id: string) {
    return this.presentResource + id + '/private-report'
  }
}
