import { Component } from '@angular/core';

import { PresentStore } from 'app/shared/services/present.store';

@Component({
  selector: 'app-present-list',
  templateUrl: './present-list.component.html'
})
export class PresentListComponent {
  constructor(
    private presentStore: PresentStore
  ) {
    this.presentStore.fetch();
  }
}
