import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Rx';

import { environment } from 'environments/environment';

@Injectable()
export class AuthenticationService {
  authTokenResource = environment.apiUrl + 'oauth/token';

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<string> {
    const headers = new HttpHeaders().set('Authorization', 'Basic ' + btoa(environment.appId + ':'));
    const body = new HttpParams()
      .set('grant_type', 'password')
      .set('username', username)
      .set('password', password);

    return this.http.post(this.authTokenResource, body, {
      headers: headers
    }).map((res: any) => res.access_token);
  }
}
