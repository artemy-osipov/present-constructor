import { Injectable } from '@angular/core';

import { Present } from 'app/shared/present.model';

@Injectable()
export class PresentStore {
  presents: Present[] = [];

  get orderedPresents(): Present[] {
    return this.presents.sort((x, y) => x.date.getTime() - y.date.getTime());
  }

  delete(present: Present) {
    this.presents = this.presents.filter(c => c.id !== present.id);
  }
}
