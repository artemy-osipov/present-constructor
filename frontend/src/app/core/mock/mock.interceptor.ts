import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';

import { mock } from './mock.data';

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const { url, method, body } = request;

    return of(null)
      .pipe(mergeMap(handleRoute))
      .pipe(materialize())
      .pipe(delay(500))
      .pipe(dematerialize());

    function handleRoute() {
      switch (true) {
        case url.endsWith('/api/candies/') && method === 'GET':
          return getCandies();
        case url.match(/\/api\/candies\/\d+$/) && method === 'GET':
          return getCandyById();
        case url.endsWith('/api/candies/') && method === 'POST':
          return addCandy();
        case url.match(/\/api\/candies\/\d+$/) && method === 'PUT':
          return updateCandy();
        case url.match(/\/api\/candies\/\d+$/) && method === 'DELETE':
          return deleteCandy();
        case url.endsWith('/api/presents/') && method === 'GET':
          return getPresents();
        case url.match(/\/api\/presents\/\d+$/) && method === 'GET':
          return getPresentById();
        case url.endsWith('/api/presents/') && method === 'POST':
          return addPresent();
        case url.match(/\/api\/presents\/\d+$/) && method === 'DELETE':
          return deletePresent();
        default:
          return next.handle(request);
      }
    }

    function getCandies() {
      return ok(mock.candies);
    }

    function getCandyById() {
      const candy = mock.candies.find(x => x.id === idFromUrl());

      if (candy) {
        return ok(candy);
      } else {
        return empty();
      }
    }

    function addCandy() {
      mock.candies.push(body);

      return added(Math.random().toString(36));
    }

    function updateCandy() {
      const index = mock.candies.findIndex(x => x.id === idFromUrl());
      mock.candies[index] = body;

      return ok();
    }

    function deleteCandy() {
      mock.candies = mock.candies.filter(x => x.id !== idFromUrl());
      return ok();
    }

    function getPresents() {
      return ok(mock.presents);
    }

    function getPresentById() {
      const present = mock.presents.find(x => x.id === idFromUrl());

      if (present) {
        return ok(present);
      } else {
        return empty();
      }
    }

    function addPresent() {
      mock.presents.push(body);

      return added(Math.random().toString(36));
    }

    function deletePresent() {
      mock.presents = mock.presents.filter(x => x.id !== idFromUrl());
      return ok();
    }

    function ok(content?: any) {
      return of(new HttpResponse({ status: 200, body: content }));
    }

    function added(id: string) {
      return of(new HttpResponse({ status: 201, body: id }));
    }

    function empty() {
      return of(new HttpResponse({ status: 404 }));
    }

    function idFromUrl() {
      const urlParts = url.split('/');
      return urlParts[urlParts.length - 1];
    }
  }
}

export const fakeBackendProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true
};
