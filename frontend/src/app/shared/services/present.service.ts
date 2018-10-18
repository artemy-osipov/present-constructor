import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Present } from 'app/shared/model/present.model';
import { environment } from 'environments/environment';

@Injectable()
export class PresentService {
  presentResource = environment.apiUrl + 'api/presents/';

  constructor(private http: HttpClient) { }

  add(present: Present): Observable<string> {
    return this.http.post(this.presentResource, present, { observe: 'response' })
      .map(resp => this.getIdFromLocation(resp.headers.get('Location')));
  }

  private getIdFromLocation(location: string): string {
    if (location.search(this.presentResource) === 0) {
      return location.substring(this.presentResource.length);
    }
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>(this.presentResource + id)
      .map(data => new Present(data));
  }

  list(): Observable<Present[]> {
    return this.http.get<Object[]>(this.presentResource)
      .map(res => res.map(data => new Present(data)));
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
