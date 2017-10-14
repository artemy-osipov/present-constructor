import { Injectable } from '@angular/core';
import { action, computed, observable } from 'mobx-angular';

import { Present } from 'app/shared/model/present.model';

@Injectable()
export class PresentStore {
  @observable presents: Present[] = [];

  @computed get orderedPresents(): Present[] {
    return this.presents.sort((x, y) => x.date.getTime() - y.date.getTime());
  }

  @action delete(present: Present) {
    this.presents = this.presents.filter(c => c.id !== present.id);
  }
}
