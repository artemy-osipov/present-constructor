import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

import { Present } from 'app/shared/model/present.model';
import { environment } from 'environments/environment';

@Injectable()
export class PresentService {
  presentHost = environment.apiUrl + '/presents/';

  constructor(private http: HttpClient) { }

  add(present: Present): Observable<string> {
    return this.http.post(this.presentHost, present, { observe: 'response' })
      .map(resp => this.getIdFromLocation(resp.headers.get('Location')));
  }

  private getIdFromLocation(location: string): string {
    if (location.search(this.presentHost) === 0) {
      return location.substring(this.presentHost.length);
    }
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>(this.presentHost + id)
      .map(data => new Present(data));
  }

  list(): Observable<Present[]> {
    return this.http.get<Object[]>(this.presentHost)
      .map(res => res.map(data => new Present(data)));
  }

  delete(present: Present): Observable<Object> {
    return this.http.delete(this.presentHost + present.id);
  }
}
