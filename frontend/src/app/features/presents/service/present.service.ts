import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

import { PresentGateway } from 'app/core/api/present.gateway'
import { Present as PresentDTO } from 'app/core/api/present.dto'
import { Present } from './present.model'

@Injectable({ providedIn: 'root' })
export class PresentService {
  constructor(private gateway: PresentGateway) {}

  list(): Observable<Present[]> {
    return this.gateway.list().pipe(map((ps) => ps.map((p) => new Present(p))))
  }

  getPresent(id: string): Observable<Present> {
    return this.gateway.get(id).pipe(map((p) => new Present(p)))
  }

  add(present: PresentDTO): Observable<string> {
    return this.gateway.add(present).pipe(
      map((id) => {
        present.id = id
        return id
      })
    )
  }

  delete(id: string): Observable<Object> {
    return this.gateway.delete(id)
  }

  publicReportLocation(id: string) {
    return this.gateway.publicReportLocation(id)
  }

  privateReportLocation(id: string) {
    return this.gateway.privateReportLocation(id)
  }
}
