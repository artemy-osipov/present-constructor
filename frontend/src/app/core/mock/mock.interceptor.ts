import { Injectable } from '@angular/core'
import {
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
} from '@angular/common/http'
import { Observable, of } from 'rxjs'
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators'

import { ID } from '@datorama/akita'
import { Mock } from './mock.data'

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
  private mock = new Mock()

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const { url, method, body } = request
    const self = this

    function ok(content?: any) {
      return of(new HttpResponse({ status: 200, body: content }))
    }

    function added(id: ID) {
      return of(new HttpResponse({ status: 201, body: id }))
    }

    function empty() {
      return of(new HttpResponse({ status: 404 }))
    }

    function idFromUrl() {
      const urlParts = url.split('/')
      return urlParts[urlParts.length - 1]
    }

    function getCandies() {
      return ok(self.mock.candies)
    }

    function getCandyById() {
      const candy = self.mock.getCandy(idFromUrl())

      if (candy) {
        return ok(candy)
      }
      return empty()
    }

    function addCandy() {
      const id = self.mock.addCandy(body)
      return added(id)
    }

    function updateCandy() {
      self.mock.updateCandy(body)
      return ok()
    }

    function deleteCandy() {
      self.mock.deleteCandy(idFromUrl())
      return ok()
    }

    function getPresents() {
      return ok(self.mock.presents)
    }

    function getPresentById() {
      const present = self.mock.getPresent(idFromUrl())

      if (present) {
        return ok(present)
      }
      return empty()
    }

    function addPresent() {
      const id = self.mock.addPresent(body)
      return added(id)
    }

    function deletePresent() {
      self.mock.deletePresent(idFromUrl())
      return ok()
    }

    function handleRoute() {
      switch (true) {
        case url.endsWith('/api/candies/') && method === 'GET':
          return getCandies()
        case url.match(/\/api\/candies\/[\w-]+$/) && method === 'GET':
          return getCandyById()
        case url.endsWith('/api/candies/') && method === 'POST':
          return addCandy()
        case url.match(/\/api\/candies\/[\w-]+$/) && method === 'PUT':
          return updateCandy()
        case url.match(/\/api\/candies\/[\w-]+$/) && method === 'DELETE':
          return deleteCandy()
        case url.endsWith('/api/presents/') && method === 'GET':
          return getPresents()
        case url.match(/\/api\/presents\/[\w-]+$/) && method === 'GET':
          return getPresentById()
        case url.endsWith('/api/presents/') && method === 'POST':
          return addPresent()
        case url.match(/\/api\/presents\/[\w-]+$/) && method === 'DELETE':
          return deletePresent()
        default:
          return next.handle(request)
      }
    }

    return of(null)
      .pipe(mergeMap(handleRoute))
      .pipe(materialize())
      .pipe(delay(500))
      .pipe(dematerialize())
  }
}

export const fakeBackendProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: FakeBackendInterceptor,
  multi: true,
}