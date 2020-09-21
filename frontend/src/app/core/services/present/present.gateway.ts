import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { ID } from '@datorama/akita'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

import { Present } from 'app/core/models/present.model'
import { environment } from 'environments/environment'

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

  get(id: ID): Observable<Present> {
    return this.http
      .get<Present>(this.presentResource + id)
      .pipe(map((data) => new Present(data)))
  }

  list(): Observable<Present[]> {
    return this.http
      .get<Present[]>(this.presentResource)
      .pipe(map((res) => res.map((data) => new Present(data))))
  }

  delete(id: ID): Observable<Object> {
    return this.http.delete(this.presentResource + id)
  }

  publicReportLocation(id: ID) {
    return this.presentResource + id + '/public-report'
  }

  privateReportLocation(id: ID) {
    return this.presentResource + id + '/private-report'
  }
}
