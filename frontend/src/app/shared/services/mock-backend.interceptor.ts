import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { environment } from 'environments/environment';
import { Candy } from 'app/shared/model/candy.model';
import { Present } from 'app/shared/model/present.model';

@Injectable()
export class MockBackendInterceptor implements HttpInterceptor {

    private candies: Candy[] = [];
    private presents: Present[] = [];

    constructor() {
        for (let index = 0; index < 41; index++) {
            this.candies.push(this.sampleCandy(index.toString()));
        }
        for (let index = 0; index < 11; index++) {
            this.presents.push(this.samplePresent(index.toString()));
        }
    }

    sampleCandy(id: string): Candy {
        return new Candy({
            id: id,
            name: 'some name ' + id,
            firm: 'some firm ' + id,
            price: 123.32,
            order: parseInt(id, 10)
        });
    }

    samplePresent(id: string): Present {
        return new Present({
            id: id,
            name: 'some name ' + id,
            price: 123.32,
            date: new Date,
            items: [
                {
                    candy: this.sampleCandy('1'),
                    count: 1
                },
                {
                    candy: this.sampleCandy('3'),
                    count: 3
                },
                {
                    candy: this.sampleCandy('10'),
                    count: 10
                }
            ]
        });
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const { url, method, body } = request;
        const it = this;

        return of(null)
            .pipe(mergeMap(handleRoute));

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
            return ok(it.candies);
        }

        function getCandyById() {
            const candy = it.candies.find(x => x.id === idFromUrl());

            if (candy) {
                return ok(candy);
            } else {
                return empty();
            }
        }

        function addCandy() {
            it.candies.push(body);

            return added(Math.random().toString(36));
        }

        function updateCandy() {
            const index = it.candies.findIndex(x => x.id === idFromUrl());
            it.candies[index] = body;

            return ok();
        }

        function deleteCandy() {
            it.candies = it.candies.filter(x => x.id !== idFromUrl());
            return ok();
        }

        function getPresents() {
            return ok(it.presents);
        }

        function getPresentById() {
            const present = it.presents.find(x => x.id === idFromUrl());

            if (present) {
                return ok(present);
            } else {
                return empty();
            }
        }

        function addPresent() {
            it.presents.push(body);

            return added(Math.random().toString(36));
        }

        function deletePresent() {
            it.presents = it.presents.filter(x => x.id !== idFromUrl());
            return ok();
        }

        function ok(content?: any) {
            return of(new HttpResponse({ status: 200, body: content }));
        }

        function added(id: string) {
            return of(new HttpResponse({ status: 201, headers: new HttpHeaders({'Location': environment.apiUrl + 'api/candies/' + id}) }));
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
