import { Injectable } from '@angular/core';
import { action, computed, observable } from 'mobx-angular';

import { Present } from 'app/shared/model/present.model';
import { PresentApi } from './present.api.service';

@Injectable()
export class PresentStore {
  @observable presents: Present[] = [];

  constructor(private presentApi: PresentApi) {
  }

  @action fetch() {
    this.presentApi.list().subscribe(
      presents => this.presents = presents
    );
  }

  @computed get orderedPresents(): Present[] {
    return this.presents.sort((x, y) => x.date.getTime() - y.date.getTime());
  }

  @action delete(present: Present) {
    this.presentApi.delete(present).subscribe(
      () => this.presents = this.presents.filter(c => c.id !== present.id)
    );
  }
}
