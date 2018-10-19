import { HttpHeaders } from '@angular/common/http';

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
