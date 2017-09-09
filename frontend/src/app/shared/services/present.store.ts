import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs/Rx';

import { Present } from 'app/shared/present.model';

@Injectable()
export class PresentStore {
  presents: Present[] = [];

  constructor(private http: HttpClient) { }

  get orderedPresents(): Present[] {
    return this.presents.sort((x, y) => new Date(x.date).getTime() - new Date(y.date).getTime());
  }

  fetch(): Observable<Present[]> {
    const res: Observable<Present[]> = this.http.get<Present[]>('http://localhost:8080/presents');
    res.subscribe(data => {
      this.presents = data;
    });

    return res;
  }

  get(id: string): Observable<Present> {
    return this.http.get<Present>('http://localhost:8080/presents/' + id);
  }

  add(present: Present): Observable<any> {
    const res: Observable<any> = this.http.post<string>('http://localhost:8080/presents', present, { observe: 'response' });

    return res;
  }

  delete(present: Present): Observable<any> {
    const res: Observable<Present> = this.http.delete('http://localhost:8080/presents/' + present.id);
    res.subscribe(data => {
      this.presents = this.presents.filter(c => c.id !== present.id);
    });

    return res;
  }
}
