import { Component } from '@angular/core';

import { CandyStore } from 'app/shared/services/candy.store';

@Component({
  selector: 'app-candy-list',
  templateUrl: './candy-list.component.html'
})
export class CandyListComponent {
  constructor(
    private candyStore: CandyStore
  ) {
    this.candyStore.fetch();
  }
}
