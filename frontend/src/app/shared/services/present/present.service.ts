import { Injectable } from "@angular/core";
import { ID } from "@datorama/akita";
import { Observable } from "rxjs";
import { tap, map } from "rxjs/operators";

import { Present } from "app/shared/model/present.model";
import { PresentStore } from "./present.store";
import { PresentGateway } from "./present.gateway";

@Injectable({ providedIn: 'root' })
export class PresentService {

  constructor(
    private store: PresentStore,
    private gateway: PresentGateway
  ) { }

  list(): Observable<Present[]> {
    return this.gateway.list().pipe(
      tap(presents => this.store.set(presents))
    );
  }

  getPresent(id: ID): Observable<Present> {
    return this.gateway.get(id).pipe(
      tap(present => this.store.upsert(id, present))
    );
  }

  add(present: Present): Observable<Present> {
    return this.gateway.add(present).pipe(
      map(id => {
        present.id = id
        this.store.upsert(id, present)

        return present
      })
    );
  }

  delete(id: ID): Observable<Object> {
    return this.gateway.delete(id).pipe(
      tap(_ => this.store.remove(id))
    );
  }

  publicReportLocation(id: ID) {
    return this.gateway.publicReportLocation(id);
  }

  privateReportLocation(id: ID) {
    return this.gateway.privateReportLocation(id);
  }
}
