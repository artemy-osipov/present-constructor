import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Present } from 'app/shared/model/present.model';
import { environment } from 'environments/environment';
import { ApiHelper } from './api-helper.service';

@Injectable()
export class PresentApi {
  presentResource = environment.apiUrl + 'api/presents/';

  constructor(private http: HttpClient) { }

  add(present: Present): Observable<string> {
    return this.http.post(this.presentResource, present, { observe: 'response' })
      .pipe(map(resp => ApiHelper.extractNewId(resp.headers, this.presentResource)));
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>(this.presentResource + id)
      .pipe(map(data => new Present(data)));
  }

  list(): Observable<Present[]> {
    return this.http.get<Present[]>(this.presentResource)
      .pipe(map(res => res.map(data => new Present(data))));
  }

  delete(present: Present): Observable<Object> {
    return this.http.delete(this.presentResource + present.id);
  }

  publicReportLocation(id: string) {
    return this.presentResource + id + '/public-report';
  }

  privateReportLocation(id: string) {
    return this.presentResource + id + '/private-report';
  }
}
