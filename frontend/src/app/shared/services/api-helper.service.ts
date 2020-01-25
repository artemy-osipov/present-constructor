import { HttpHeaders, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MockBackendInterceptor } from './mock-backend.interceptor';

export class ApiHelper {
  static extractNewId(headers: HttpHeaders, resource: string): string {
    const location = headers.get('Location');
    if (location && location.search(resource) === 0) {
      return location.substring(resource.length);
    } else {
      throw new Error(`can't parse Location: ${location}`);
    }
  }
}

export const fakeBackendProvider = {
  // use fake backend in place of Http service for backend-less development
  provide: HTTP_INTERCEPTORS,
  useClass: MockBackendInterceptor,
  multi: true
};
